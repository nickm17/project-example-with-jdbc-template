package com.example.demo.repository;

import com.example.demo.domain.entity.Car;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class CarRepositoryImplTest {

    @InjectMocks
    private CarRepositoryImpl carRepository;
    @Mock
    private JdbcTemplate jdbcTemplate;

    private Car car1 = null;
    private  Car car2 = null;

    @Before
    public void setup() {
        car1 = Car.builder().carId(125422222).brand("Audi").color("Negru").speed(200).build();
        car2 = Car.builder().carId(1254).brand("Audi").color("Negru").speed(200).build();
    }

    @Test
    public void test_find_all(){
       List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(cars);

        List<Car> result = carRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualToComparingFieldByField(car2);
        assertThat(result.get(1)).isEqualToComparingFieldByField(car1);
    }
}
