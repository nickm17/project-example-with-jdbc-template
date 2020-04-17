package com.example.demo.repository;

import com.example.demo.domain.entity.Customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> findAll();

//    Customer findById(int id);

    Customer save (Customer customer);
}
