package pl.pjatk.mprProject.Service;

import org.springframework.stereotype.Service;
import pl.pjatk.mprProject.Exception.CarAlreadyExistsException;
import pl.pjatk.mprProject.Exception.CarNotFoundException;
import pl.pjatk.mprProject.Model.Car;
import pl.pjatk.mprProject.Repository.CarRepository;


import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car createCar(Car car) {
        Car existingCar = carRepository.findByRegistrationCar(car.getRegistrationCar());
        if (existingCar != null && existingCar.getRegistrationCar().equals(car.getRegistrationCar())) {
            throw new CarAlreadyExistsException("Samochód o rejestracji '" + existingCar.getRegistrationCar() + "' już istnieje.");
        }
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException("Nie znaleziono samochodu o id: " + id);
        }
        if(carRepository.count() == 0){
            throw new CarNotFoundException("Baza danych jest pusta!");
        }
        carRepository.deleteById(id);
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Nie znaleziono samochodu o id: " + id));
    }

    @Override
    public Car updateCar(Long id, Car car) {
        Car updatingCar = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Nie znaleziono samochodu o id: " + id));

        if(carRepository.count() == 0){
            throw new CarNotFoundException("Baza danych jest pusta!");
        }

        updatingCar.setBrandCar(car.getBrandCar());
        updatingCar.setModelCar(car.getModelCar());
        updatingCar.setRegistrationCar(car.getRegistrationCar());
        updatingCar.setMileage(car.getMileage());

        return carRepository.save(updatingCar);
    }
}
