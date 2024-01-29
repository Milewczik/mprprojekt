package pl.pjatk.mprProject.Service;

import pl.pjatk.mprProject.Model.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();
    Car createCar(Car car);
    void deleteCar(Long id);
    Car getCarById(Long id);
    Car updateCar(Long id, Car car);

}
