package com.example.demo.domain.model;

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
public class CarPatch {

    @NotBlank
    private String color;
    @Max(250)
    @Min(50)
    private int speed;

    @Max(250)
    @Min(50)
    private int carId;
}
