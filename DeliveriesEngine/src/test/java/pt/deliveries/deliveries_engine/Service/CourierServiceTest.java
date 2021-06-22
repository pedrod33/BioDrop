package pt.deliveries.deliveries_engine.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Exception.CourierEmailAndPasswordDoNotMatchException;
import pt.deliveries.deliveries_engine.Exception.CourierEmailOrPhoneNumberInUseException;
import pt.deliveries.deliveries_engine.Exception.SupervisorOrVehicleTypeAssociationException;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Pojo.LoginCourierPojo;
import pt.deliveries.deliveries_engine.Pojo.RegisterCourierPojo;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import pt.deliveries.deliveries_engine.Repository.SupervisorRepository;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {

    Logger logger = Logger.getLogger(CourierServiceTest.class.getName());


    @Mock(lenient = true)
    private CourierRepository courierRepository;

    @Mock(lenient = true)
    private VehicleRepository vehicleRepository;

    @Mock(lenient = true)
    private SupervisorRepository supervisorRepository;

    @InjectMocks
    private CourierServiceImpl courierService;

    private Courier c1;
    private Vehicle v1;
    private Supervisor s1;

    @BeforeEach
    public void setUp(){
        c1 = new Courier("Marco Alves", "marcoA@gmail.com", "12345678", "M", 931231233L, new Supervisor("Carlos", "carlos@gmail.com","12345678"), new Vehicle("car"));
        when(courierRepository.findByEmail(Mockito.any())).thenReturn(null);
        when(courierRepository.findByEmail("marcoA@gmail.com")).thenReturn(c1);
        when(courierRepository.findByPhoneNumber(Mockito.anyLong())).thenReturn(null);
        when(courierRepository.findByPhoneNumber(931231233L)).thenReturn(c1);
        v1 = new Vehicle("car");
        s1 = new Supervisor("asas@gmail.com", "12345678", "Abilio");
        v1.setId(1L);
        when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(null);
        when(vehicleRepository.findById(1L)).thenReturn(v1);
        s1.setId(1L);
        when(supervisorRepository.findAll()).thenReturn(new ArrayList<Supervisor>(Arrays.asList(s1)));
    }
    //TODO:tests to supervisor findall
    @Test
    void whenExistingEmailPhoneNumberVID_thenReturnFalse_canRegister(){
        RegisterCourierPojo rcp = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L);
        assertThrows(CourierEmailOrPhoneNumberInUseException.class, () -> courierService.canRegister(rcp));
    }

    @Test
    void whenExistingEmailPhoneNumber_thenReturnFalse_canRegister(){
        RegisterCourierPojo rcp = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 2L);
        assertThrows(CourierEmailOrPhoneNumberInUseException.class, () -> courierService.canRegister(rcp));
    }

    @Test
    void whenExistingEmailVID_thenReturnFalse_canRegister(){
        RegisterCourierPojo rcp = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231234L, 1L);
        assertThrows(CourierEmailOrPhoneNumberInUseException.class, () -> courierService.canRegister(rcp));
    }

    @Test
    void whenExistingPhoneNumberVID_thenReturnFalse_canRegister(){
        RegisterCourierPojo rcp = new RegisterCourierPojo("Marco Alves","marco@gmail.com","12345678","M",931231233L, 1L);
        assertThrows(CourierEmailOrPhoneNumberInUseException.class, () -> courierService.canRegister(rcp));
    }



    @Test
    void whenExistingEmail_thenReturnFalse_canRegister(){
        RegisterCourierPojo rcp = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231236L, 12L);
        assertThrows(CourierEmailOrPhoneNumberInUseException.class, () -> courierService.canRegister(rcp));

    }

    @Test
    void whenExistingPhoneNumber_thenReturnFalse_canRegister(){
        RegisterCourierPojo rcp = new RegisterCourierPojo("Marco Alves","marco@gmail.com","12345678","M",931231233L, 12L);
        assertThrows(CourierEmailOrPhoneNumberInUseException.class, () -> courierService.canRegister(rcp));

    }

    @Test
    void whenExistingVID_thenReturnFalse_canRegister(){
        RegisterCourierPojo rcp = new RegisterCourierPojo("Marco Alves","marco@gmail.com","12345678","M",931231234L, 1L);
        assertThrows(SupervisorOrVehicleTypeAssociationException.class, () -> courierService.canRegister(rcp));
    }

    //verifyLogin
    @Test
    void whenMailAndPasswordCorrespond_ThenReturnCourier_verifyLogin(){
        LoginCourierPojo lcp1 = new LoginCourierPojo("marcoA@gmail.com","12345678");
        when(courierRepository.findByEmail(Mockito.any())).thenReturn(c1);
        Courier res_courier = courierService.verifyLogin(lcp1);
        logger.log(Level.INFO, res_courier.toString());
        assertThat(lcp1.getEmail()).isEqualTo(c1.getEmail());
        assertThat(lcp1.getPassword()).isEqualTo(c1.getPassword());
    }

    @Test
    void whenPasswordCorrespondsButNotEmail_ThenReturnNull_verifyLogin(){
        LoginCourierPojo lcp = new LoginCourierPojo("marco@gmail.com","12345678");
        assertThrows(CourierEmailAndPasswordDoNotMatchException.class, () -> courierService.verifyLogin(lcp));

    }

    @Test
    void whenMailCorrespondsButNotPassword_ThenReturnNull_verifyLogin(){
        LoginCourierPojo lcp = new LoginCourierPojo("marcoA@gmail.com","1234567");
        assertThrows(CourierEmailAndPasswordDoNotMatchException.class, () -> courierService.verifyLogin(lcp));
    }
}
