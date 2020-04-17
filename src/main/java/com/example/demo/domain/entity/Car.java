package com.example.demo.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@XmlRootElement
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable {

    private Integer carId;
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

    private Customer customerOfTheCar;
}
