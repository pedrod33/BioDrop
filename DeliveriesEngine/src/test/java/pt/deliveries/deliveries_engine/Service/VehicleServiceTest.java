package pt.deliveries.deliveries_engine.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Exception.SupervisorEmailIsUsedException;
import pt.deliveries.deliveries_engine.Exception.VehicleTypeIsUsedException;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Mockito.when(vehicleRepository.findByType(v1.getType())).thenReturn(v1);
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
}
