package com.example.r2dbc;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

interface ReservationRepository extends ReactiveCrudRepository<Reservation, Integer> {
  @Query("select * from reservation where name = $1")
  Flux<Reservation> findByName(String name);
}