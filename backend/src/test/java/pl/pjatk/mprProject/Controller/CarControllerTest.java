package pl.pjatk.mprProject.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import pl.pjatk.mprProject.Model.Car;
import pl.pjatk.mprProject.Model.Dto.CarDto;
import pl.pjatk.mprProject.Service.CarServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CarServiceImpl carService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setup() {
        mockMvc = standaloneSetup(carController).build();
    }

    @Test
    void testGetAllCars() throws Exception {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        verify(carService).getAllCars();
        verify(modelMapper, times(cars.size())).map(any(Car.class), eq(CarDto.class));
    }

    @Test
    void testGetCarById() throws Exception {
        Long carId = 1L;
        Car car = new Car();
        when(carService.getCarById(carId)).thenReturn(car);

        mockMvc.perform(get("/car/" + carId))
                .andExpect(status().isOk())
                .andExpect(view().name("getCar"));

        verify(carService).getCarById(carId);
        verify(modelMapper).map(car, CarDto.class);
    }

    @Test
    void testCreateCar() throws Exception {
        CarDto carDto = new CarDto();
        carDto.setRegistrationCar("Test Registration");
        carDto.setBrandCar("Test Brand");
        carDto.setModelCar("Test Model");
        carDto.setMileage(111111);

        Car car = new Car();

        when(modelMapper.map(carDto, Car.class)).thenReturn(car);

        when(carService.createCar(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/create")
                        .flashAttr("carDto", carDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(carService).createCar(any(Car.class));
        verify(modelMapper).map(carDto, Car.class);
    }


    @Test
    void testDeleteCar() throws Exception {
        Long carId = 1L;
        doNothing().when(carService).deleteCar(carId);

        mockMvc.perform(get("/delete/" + carId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(carService).deleteCar(carId);
    }

    @Test
    void testEditCar() throws Exception {
        Long carId = 1L;
        CarDto carDto = new CarDto();
        carDto.setRegistrationCar("Updated Registration");
        carDto.setBrandCar("Updated Brand");
        carDto.setModelCar("Updated Model");

        Car car = new Car();
        when(modelMapper.map(carDto, Car.class)).thenReturn(car);
        when(carService.updateCar(eq(carId), any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/edit/" + carId)
                        .flashAttr("carDto", carDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(carService).updateCar(eq(carId), any(Car.class));
        verify(modelMapper).map(carDto, Car.class);
    }



    @Test
    void testShowCreatingForm() throws Exception {
        mockMvc.perform(get("/creatingForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("creatingForm"));
    }

    @Test
    void testShowEditingForm() throws Exception {
        Long carId = 1L;
        Car car = new Car();
        when(carService.getCarById(carId)).thenReturn(car);

        mockMvc.perform(get("/editingForm/" + carId))
                .andExpect(status().isOk())
                .andExpect(view().name("editingForm"));

        verify(carService).getCarById(carId);
        verify(modelMapper).map(car, CarDto.class);
    }




}
