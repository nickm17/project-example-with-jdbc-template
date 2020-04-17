package com.example.demo.controller;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.model.CarPatch;
import com.example.demo.exception.CarNotFoundException;
import com.example.demo.service.CarService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/cars")
@RestController
public class CarController {

    private final CarService carService;

    @GetMapping(value = "/{id}", produces = "application/xml")
    @ResponseStatus(HttpStatus.OK)
    public Car getCar(@PathVariable(name = "id") int carId) {

        return carService.getCarById(carId);

//        return cars.stream().filter(car -> car.getCarId() == carId).collect(Collectors.toList()).get(0);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Car> getCars() {
        return carService.getCars();
    }

    @GetMapping("/bc")
    @ResponseStatus(HttpStatus.OK)
    public List<Car> getCarsByBrandAndColor(@RequestParam("brand") String brand,
                                            @RequestParam("color") String color) {
        return carService.getCarsByBrandAndColor(brand, color);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable(name = "id") int carId) {
        carService.deleteById(carId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@RequestBody @Valid Car car) {
        return carService.createCar(car);
    }

    @PostMapping("/list")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Car> createCarsList(@RequestBody @Valid List<Car> cars) {
        return  carService.createCars(cars);
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCars(@RequestParam("file") MultipartFile file) {
//        carService.uploadCars(file);
        carService.uploadCarsUsingJustStrings(file);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car updateCar(@RequestBody @Valid Car car) {
        return carService.updateCar(car);
    }

    @PatchMapping("/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Car updateCar(@PathVariable int carId, @RequestBody CarPatch carPatch) {
        carPatch.setCarId(carId);
        return carService.updatePartialCar(carPatch);
    }

    @ExceptionHandler(ValidationException.class)
    public void badRequest(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(CarNotFoundException.class)
    public void notFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

}
