package pl.pjatk.mprProject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class Car {
    @GeneratedValue
    @Id
    private long id;
    private String brandCar;
    private String modelCar;
    private String registrationCar;
    private long mileage;
}
