package com.example.demo.repository;

import com.example.demo.domain.entity.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class CarRepositoryImpl implements CarRepository {

    @Autowired(required = true)// required defaults to true
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteById(int id) {
        String deleteStatement = "delete from car where car_id =?";
        int updatedRows = jdbcTemplate.update(deleteStatement, id);
    }

    @Override
    public List<Car> findAll() {

        String sql = "select car_id, birth_date, brand, color, car_name, speed from car";
        RowMapper<Car> singerRowMapper = getCarRowMapper();
        List<Car> cars = jdbcTemplate.query(sql, singerRowMapper);
        Collections.sort(cars, Comparator.comparing(Car::getCarId));

        return cars;
    }

    @Override
    public List<Car> findByBrandAndColor(String brand, String color) {
        String sql = "select car_id, birth_date, brand, color, car_name, speed from car where brand = ? and color = ?";
        RowMapper<Car> singerRowMapper = getCarRowMapper();
        List<Car> carsByName = jdbcTemplate.query(sql, singerRowMapper, brand, color);
        Collections.sort(carsByName, Comparator.comparing(Car::getCarId));

        return carsByName;
    }

    @Override
    public List<Car> findByName(String name) {
        String sql = "select car_id, birth_date, brand, color, car_name, speed from car where car_name = ?";
        RowMapper<Car> singerRowMapper = getCarRowMapper();
        List<Car> carsByName = jdbcTemplate.query(sql, singerRowMapper, name);
        Collections.sort(carsByName, Comparator.comparing(Car::getCarId));

        return carsByName;
    }

    @Override
    public Car findById(int id) {
        String sql = "select car_id, birth_date, brand, color, car_name, speed from car where car_id = ?";
        return jdbcTemplate.queryForObject(sql, getCarRowMapper(), id);
    }

    @Override
    public Car save(Car car) {
        String sql = "INSERT INTO car (birth_date, brand, color, car_name, speed) VALUES (?, ?, ?, ?, ?)";

//        jdbcTemplate.update(sql, car.getBirthDate(), car.getBrand(), car.getColor(), car.getName(), car.getSpeed());

        Object[] params = new Object[]{
                car.getBirthDate(),
                car.getBrand(),
                car.getColor(),
                car.getName(),
                car.getSpeed()};
        jdbcTemplate.update(sql, params);

        return findAll().stream().max(Comparator.comparingInt(Car::getCarId)).get();
    }

    @Override
    public Car update(Car car) {
        String sql = "UPDATE car SET color = ? , speed = ? WHERE car_id = ?";
//        Object[] params = new Object[]{
//                car.getBirthDate(),
//                car.getBrand(),
//                car.getColor(),
//                car.getName(),
//                car.getSpeed()};
        int numberOfRowsUpdated = jdbcTemplate.update(sql, car.getColor(), car.getSpeed(), car.getCarId());

        if (numberOfRowsUpdated != 1){
            throw new RuntimeException();
        }

        return findById(car.getCarId());
    }

    private RowMapper<Car> getCarRowMapper() {
        return (rs, rowNum) -> {
            Car car = Car.builder()
                         .carId(rs.getInt("car_id"))
                         .birthDate(rs.getDate("birth_date").toLocalDate())
                         .brand(rs.getString("brand"))
                         .name(rs.getString("car_name"))
                         .color(rs.getString("color"))
                         .speed(rs.getInt("speed"))
                         .build();
            return car;
        };
    }
}
