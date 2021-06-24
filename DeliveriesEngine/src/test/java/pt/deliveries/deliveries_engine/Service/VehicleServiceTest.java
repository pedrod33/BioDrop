package pt.deliveries.deliveries_engine.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Exception.SupervisorEmailIsUsedException;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeDoesNotExistException;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeIsUsedException;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock(lenient = true)
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle v1;

    @BeforeEach
    public void setUp(){
        v1 = new Vehicle("car");
        v1.setId(1L);
        when(vehicleRepository.findByType(v1.getType())).thenReturn(v1);
    }

    @Test
    void whenExistingType_thenReturnTrue_exists(){
        assertThrows(VehicleTypeIsUsedException.class, () -> vehicleService.exists(v1.getType()));
    }
    @Test
    void whenNonExistingType_thenReturnFalse_exists(){
        boolean doesCourierTypeMatch = vehicleService.exists("bike");
        assertThat(doesCourierTypeMatch).isFalse();
    }

    @Test
    void whenNoVehicle_thenReturnEmpty_findAll(){
        when(vehicleRepository.findAll()).thenReturn(new ArrayList<>());
        List<Vehicle> allVehicles = vehicleService.findAllVehicles();
        assertThat(allVehicles).isEqualTo(new ArrayList<>());
    }

    @Test
    void whenVehiclesAvailable_thenReturnAllVehicles_findAll(){
        Vehicle v2 = new Vehicle("bike");
        v2.setId(2L);
        when(vehicleRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(v1, v2)));
        List<Vehicle> allVehicles = vehicleService.findAllVehicles();
        assertThat(allVehicles.size()).isEqualTo(2);
        assertThat(allVehicles).isEqualTo(new ArrayList<>(Arrays.asList(v1,v2)));
    }

    @Test
    void whenVehicleIdAvailable_thenReturnVehicle_findById(){
        Vehicle v2 = new Vehicle("bike");
        v2.setId(2L);
        when(vehicleRepository.findById(1L)).thenReturn(v1);
        Vehicle vehicle = vehicleService.findVehicleById(1L);
        assertThat(vehicle).isEqualTo(v1);
    }

    @Test
    void whenVehicleIdUnavailable_thenReturnException_findById(){
        Vehicle v2 = new Vehicle("bike");
        v2.setId(2L);
        when(vehicleRepository.findById(3L)).thenReturn(null);
        assertThrows(VehicleTypeDoesNotExistException.class, () -> vehicleService.findVehicleById(3L));
    }
}
