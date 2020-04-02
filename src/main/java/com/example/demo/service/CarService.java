package com.example.demo.service;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.model.CarPatch;
import com.example.demo.repository.CarRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarService {

    // @Autowired nu e necesar , injectarea se face in constructor
    private final CarRepository carRepository;

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    public Car getCarById(int id) {
        return carRepository.findById(id);
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Car updateCar(Car car) {
        return carRepository.update(car);
    }

    public Car updatePartialCar(@Valid CarPatch carPatch) {
        Car car = carRepository.findById(carPatch.getCarId());
        car.setColor(carPatch.getColor());
        car.setSpeed(carPatch.getSpeed());
        return carRepository.save(car);
    }
}
