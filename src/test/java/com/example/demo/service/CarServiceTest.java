package com.example.demo.service;

import com.example.demo.domain.entity.Car;
import com.example.demo.repository.CarRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;
    @Mock
    private CarRepository carRepository;

    String fileString = "";

    @Before
    public void setup () {
        fileString = "Forester,Alb,Subaru,300,2020-03-30\n";
    }

    @Test
    public void test_create_car(){
        Car carRequest = Car.builder().brand("Audi").color("Negru").speed(200).build();
        Car savedCar = Car.builder().carId(1254).brand("Audi").color("Negru").speed(200).build();


        when(carRepository.save(carRequest)).thenReturn(savedCar);
//        doReturn(savedCar).when(carRepository).save(carRequest);

        Car result = carService.createCar(carRequest);


        assertThat(result).isInstanceOf(Car.class);
        assertThat(result.getColor()).isEqualTo("Negru");

        assertThat(result).isEqualToComparingFieldByField(savedCar);
    }

    @Test
    public void given_empty_file_when_uploading_cars_then_IAE_is_thrown(){

        String fileString = "";
        MockMultipartFile file = new MockMultipartFile("cars.csv", "test", "text/csv", fileString.getBytes());

        Throwable iae = catchThrowable(() ->carService.uploadCars(file));
        assertThat(iae).isInstanceOf(IllegalArgumentException.class);
        assertThat(iae.getMessage()).isEqualTo("File is empty");
    }

    @Test
    public void test_upload_cars_file(){
        MockMultipartFile file = new MockMultipartFile("cars.csv", "test", "text/csv", fileString.getBytes());


//        when(carRepository.save(any())).thenThrow(IOException.class);
        doThrow(RuntimeException.class).when(carRepository).save(any());

        Throwable re = catchThrowable(() ->  carService.uploadCars(file));
        assertThat(re).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void given_multiple_cars_when_uploading_cars_then_all_are_saved(){

        String fileString = "Forester,Alb,Subaru,300,2020-03-30\n" +
                "Forester,Alb,Subaru,300,2020-03-30\n" +
                "Forester,Alb,Subaru,300,2020-03-30\n" +
                "Forester,Alb,Subaru,300,2020-03-30\n" +
                "Forester,Alb,Subaru,300,2020-03-30\n" +
                "Forester,Alb,Subaru,300,2020-03-30\n";
        MockMultipartFile file = new MockMultipartFile("cars.csv", "test", "text/csv", fileString.getBytes());

        carService.uploadCars(file);

        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository, times(6)).save(carArgumentCaptor.capture());
        verifyNoMoreInteractions(carRepository);

        List<Car> capturedList = carArgumentCaptor.getAllValues();
        capturedList.forEach(car -> checkCar(car));

    }

    private void checkCar(Car captured) {
        assertThat(captured.getColor()).isEqualTo("Alb");
        assertThat(captured.getBirthDate()).isEqualTo(LocalDate.parse("2020-03-30"));
        assertThat(captured.getName()).isEqualTo("Forester");
        assertThat(captured.getBrand()).isEqualTo("Subaru");
        assertThat(captured.getSpeed()).isEqualTo(300);
    }

}
