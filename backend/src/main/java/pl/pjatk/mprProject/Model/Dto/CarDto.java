package pl.pjatk.mprProject.Model.Dto;

import lombok.Data;

@Data
public class CarDto {
    private long id;
    private String brandCar;
    private String modelCar;
    private String registrationCar;
    private long mileage;
}
