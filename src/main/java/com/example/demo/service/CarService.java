package com.example.demo.service;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.model.CarPatch;
import com.example.demo.repository.CarRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CarService {

    // @Autowired nu e necesar , injectarea se face in constructor
    private final CarRepository carRepository;


    public List<Car> createCars(List<Car> cars) {
        List<Car> returnedCars = new ArrayList<>();
        cars.forEach(car -> returnedCars.add(createCar(car)));
        return returnedCars;
    }

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

    public void uploadCars(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (!StringUtils.isEmpty(line = br.readLine())) {

                String[] car = line.split(",");

                Car carObject = Car.builder()
                                   .name(car[0])
                                   .color(car[1])
                                   .brand(car[2])
                                   .speed(Integer.parseInt(car[3]))
                                   .birthDate(LocalDate.parse(car[4]))
                                   .build();

                carRepository.save(carObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void uploadCarsUsingJustStrings(MultipartFile file) {
        String[] rows = new String[0];
        try {
            rows = new String(file.getBytes()).split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String row : rows) {
            String[] car = row.split(",");

            Car carObject = Car.builder()
                               .name(car[0])
                               .color(car[1])
                               .brand(car[2])
                               .speed(Integer.parseInt(car[3]))
                               .birthDate(LocalDate.parse(car[4]))
                               .build();

            carRepository.save(carObject);
        }
    }
}
