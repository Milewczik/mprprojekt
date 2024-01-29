package pl.pjatk.mprProject.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pjatk.mprProject.Exception.CarAlreadyExistsException;
import pl.pjatk.mprProject.Exception.CarNotFoundException;
import pl.pjatk.mprProject.Model.Car;
import pl.pjatk.mprProject.Repository.CarRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllCars() {
        // Mockowanie zachowania i testowanie
    }

    @Test
    void shouldCreateCar() {
        Car newCar = new Car();
        newCar.setRegistrationCar("Test Car");

        when(carRepository.findByRegistrationCar("Test Car")).thenReturn(null);
        when(carRepository.save(any(Car.class))).thenReturn(newCar);

        Car created = carService.createCar(newCar);

        assertNotNull(created);
        verify(carRepository).save(newCar);
    }

    @Test
    void shouldThrowCarAlreadyExistsException() {
        Car existingCar = new Car();
        existingCar.setRegistrationCar("Existing Car");

        when(carRepository.findByRegistrationCar("Existing Car")).thenReturn(existingCar);

        Car newCar = new Car();
        newCar.setRegistrationCar("Existing Car");

        assertThrows(CarAlreadyExistsException.class, () -> {
            carService.createCar(newCar);
        });
    }

    @Test
    void shouldDeleteCar() {
        Long id = 1L;
        when(carRepository.existsById(id)).thenReturn(true);

        carService.deleteCar(id);

        verify(carRepository).deleteById(id);
    }

    @Test
    void shouldThrowCarNotFoundExceptionOnDelete() {
        Long id = 1L;
        when(carRepository.existsById(id)).thenReturn(false);

        assertThrows(CarNotFoundException.class, () -> {
            carService.deleteCar(id);
        });
    }

    @Test
    void shouldGetCarById() {
        Long id = 1L;
        Car car = new Car();
        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        Car found = carService.getCarById(id);

        assertNotNull(found);
        assertEquals(car, found);
    }

    @Test
    void shouldThrowCarNotFoundExceptionOnGetById() {
        Long id = 1L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> {
            carService.getCarById(id);
        });
    }

    @Test
    void shouldUpdateCar() {
        Long id = 1L;
        Car existingCar = new Car();
        existingCar.setId(id);
        existingCar.setRegistrationCar("Original Registration");

        when(carRepository.findById(id)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(any(Car.class))).thenReturn(existingCar);

        Car updatedCar = new Car();
        updatedCar.setRegistrationCar("Updated Registration");

        carService.updateCar(id, updatedCar);

        verify(carRepository).save(existingCar);
        assertEquals("Updated Registration", existingCar.getRegistrationCar());
    }

    @Test
    void shouldThrowCarNotFoundExceptionOnUpdate() {
        Long id = 1L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        Car updatedCar = new Car();

        assertThrows(CarNotFoundException.class, () -> {
            carService.updateCar(id, updatedCar);
        });
    }
}
