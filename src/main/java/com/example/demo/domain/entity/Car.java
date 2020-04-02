package com.example.demo.domain.entity;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private int carId;
    @NotBlank
    private String color;
    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @Max(250)
    @Min(50)
    private int speed;

    private LocalDate birthDate;
}
