package pl.pjatk.mprProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pjatk.mprProject.Model.Car;

public interface CarRepository extends JpaRepository<Car,Long> {
    Car findByRegistrationCar(String name);
}
