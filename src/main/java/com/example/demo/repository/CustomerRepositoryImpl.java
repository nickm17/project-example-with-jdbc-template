package com.example.demo.repository;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired(required = true)// required defaults to true
    private JdbcTemplate jdbcTemplate;

    @Autowired
    List<Car> carsFormSpringContext;

    @Override
    public List<Customer> findAll() {
        System.out.println(carsFormSpringContext);



        String sql = "select \"customerId\", birthdate, lastName, firstName, address, age, \"carId\" from customer";
        RowMapper<Customer> singerRowMapper = getCarRowMapper();
        List<Customer> customers = jdbcTemplate.query(sql, singerRowMapper);
        Collections.sort(customers, Comparator.comparing(Customer::getCustomerId));

        return customers;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    private RowMapper<Customer> getCarRowMapper() {
        return (rs, rowNum) -> {
            Customer customer = Customer.builder()
                         .customerId(rs.getInt("customerId"))
                                        .birthDate(rs.getDate("birthdate").toLocalDate())
                                        .lastName(rs.getString("lastName"))
                                        .firstName(rs.getString("firstName"))
                                        .address(rs.getString("address"))
                                        .age(rs.getInt("age"))
                                        .carId(rs.getInt("carId"))
                                        .build();
            return customer;
        };
    }
}
