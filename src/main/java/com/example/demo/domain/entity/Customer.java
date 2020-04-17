package com.example.demo.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Customer implements Serializable {

    private String address;
    private int age;
    private LocalDate birthDate;
    private Integer customerId;
    private String firstName;
    private String lastName;

    private int carId;
}
