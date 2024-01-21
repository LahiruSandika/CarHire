package service.custom;

import dto.CarDto;
import service.SuperService;

import java.util.List;

public interface CarService extends SuperService {
    String saveCar(CarDto carDto);

    String updateCar(CarDto carDto);

    String deleteCar(String id);

    CarDto getCar(String id);

    List<CarDto> getAllCars();
}
