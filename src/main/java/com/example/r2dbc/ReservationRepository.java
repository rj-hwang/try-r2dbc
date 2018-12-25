package com.example.r2dbc;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ReservationRepository {
  private final ConnectionFactory connectionFactory;

  ReservationRepository(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  Mono<Void> deleteById(Integer id) {
    return this.connection()
      .flatMapMany(c -> c
        .createStatement("delete from reservation where id = $1")
        .bind("$1", id).execute())
      .then();
  }

  Flux<Reservation> findAll() {
    return this.connection()
      .flatMapMany(c -> Flux
        .from(c.createStatement("select * from reservation").execute())
        .flatMap((Result r) -> r.map((row, rowMetadata) -> new Reservation(
          row.get("id", Integer.class),
          row.get("name", String.class)
        ))));
  }

  Flux<Reservation> save(Reservation r) {
    Flux<? extends Result> flatMapMany = this.connection()
      .flatMapMany(c -> c
        .createStatement("insert into reservation(name) values($1)")
        .bind("$1", r.getName())
        .add()
        .execute());
    return flatMapMany.switchMap(x -> Flux.just(new Reservation(r.getId(), r.getName())));
  }

  private Mono<Connection> connection() {
    return Mono.from(connectionFactory.create());
  }
}
