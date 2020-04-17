package com.example.demo.config;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    @Bean
    public DataSource postgressqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/jdbcdb");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
//        dataSource.setSchema("");

        return dataSource;
    }

    @Bean
    @Scope("prototype")
    public List<Car> getCarList () {
        return List.of(new Car());
    }

    @Bean
    public ObjectMapper getObjectMapper () {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper;
    }
    @Bean
    @Scope("prototype")
    public List<Customer> getCustomersList () {
        return List.of(new Customer());
    }

}
