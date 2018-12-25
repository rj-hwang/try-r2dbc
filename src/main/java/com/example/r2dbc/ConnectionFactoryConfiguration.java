package com.example.r2dbc;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.function.DatabaseClient;

@Configuration
public class ConnectionFactoryConfiguration {
  @Bean
  DatabaseClient databaseClient() {
    return DatabaseClient.create(connectionFactory());
  }

  @Bean
  ConnectionFactory connectionFactory() {
    PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder()
      .database("try_r2dbc")
      .host("localhost")
      .username("test")
      .password("password")
      .build();
    return new PostgresqlConnectionFactory(config);
  }
}

