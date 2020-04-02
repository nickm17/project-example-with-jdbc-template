package com.example.demo.repository;

import com.example.demo.domain.entity.Car;

import java.util.List;

public interface CarRepository {

    void deleteById(int id);

    List<Car> findAll();

    List<Car> findByName(String name);

    Car findById(int id);

    Car save(Car car);

    Car update(Car car);
}
