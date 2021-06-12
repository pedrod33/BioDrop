package pt.deliveries.deliveries_engine.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Model.Courier;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Model.Vehicle;
import pt.deliveries.deliveries_engine.Pojo.LoginCourierPojo;
import pt.deliveries.deliveries_engine.Pojo.RegisterCourierPojo;
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import pt.deliveries.deliveries_engine.Repository.VehicleRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {

    Logger logger = Logger.getLogger(CourierServiceTest.class.getName());

    private final RegisterCourierPojo REGISTER_POJO_WRONG_PHONE = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L, 1L);
    private final RegisterCourierPojo REGISTER_POJO_WRONG_EMAIL = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L, 1L);
    private final RegisterCourierPojo REGISTER_POJO_WRONG_PHONE_AND_EMAIL = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L, 1L);
    private final LoginCourierPojo LOGIN_COURIER_POJO_WRONG_PASSWORD = new LoginCourierPojo("marcoA@gmail.com","12345677");
    private final LoginCourierPojo LOGIN_COURIER_POJO_WRONG_EMAIL = new LoginCourierPojo("marco@gmail.com","12345678");


    @Mock(lenient = true)
    private CourierRepository courierRepository;

    @Mock(lenient = true)
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private CourierServiceImpl courierService;

    private Courier c1;
    private Vehicle v1;
    LoginCourierPojo lcp1;

    @BeforeEach
    public void setUp(){
        c1 = new Courier("Marco Alves", "marcoA@gmail.com", "12345678", "M", 931231233L, new Supervisor("Carlos", "carlos@gmail.com","12345678"), new Vehicle("car"));
        v1 = new Vehicle("car");
        v1.setId(1L);
        lcp1 = new LoginCourierPojo("marcoA@gmail.com","12345678");
    }

    @Test
    void whenExistingEmailOrPhoneNumberAndVehicle_ID_thenReturnTrue_exists(){
        RegisterCourierPojo rcp1 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L, 1L);
        when(courierRepository.findByEmail(rcp1.getEmail())).thenReturn(c1);
        when(courierRepository.findByPhoneNumber(rcp1.getPhoneNumber())).thenReturn(c1);
        when(vehicleRepository.findById(rcp1.getVehicle_id())).thenReturn(java.util.Optional.ofNullable(v1));
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(rcp1);
        assertThat(doesCourierEmailOrPhoneMatch).isTrue();
    }

    @Test
    void whenExistingEmailOrPhoneNumberButNotVehicle_ID_thenReturnTrue_exists(){
        RegisterCourierPojo rcp2 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 2L, 1L);
        when(courierRepository.findByEmail(rcp2.getEmail())).thenReturn(c1);
        when(courierRepository.findByPhoneNumber(rcp2.getPhoneNumber())).thenReturn(c1);
        when(vehicleRepository.findById(rcp2.getVehicle_id())).thenReturn(null);
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(rcp2);
        assertThat(doesCourierEmailOrPhoneMatch).isFalse();
    }

    @Test
    void whenExistingEmailAndVehicle_ID_thenReturnTrue_exists(){
        RegisterCourierPojo rcp1 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L, 1L);
        when(courierRepository.findByEmail(rcp1.getEmail())).thenReturn(c1);
        when(courierRepository.findByPhoneNumber(rcp1.getPhoneNumber())).thenReturn(null);
        when(vehicleRepository.findById(rcp1.getVehicle_id())).thenReturn(java.util.Optional.ofNullable(v1));
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(REGISTER_POJO_WRONG_PHONE);
        assertThat(doesCourierEmailOrPhoneMatch).isTrue();
    }

    @Test
    void whenExistingEmail_thenReturnTrue_exists(){
        RegisterCourierPojo rcp2 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 2L, 1L);
        when(courierRepository.findByEmail(rcp2.getEmail())).thenReturn(c1);
        when(courierRepository.findByPhoneNumber(rcp2.getPhoneNumber())).thenReturn(null);
        when(vehicleRepository.findById(Mockito.any())).thenReturn(null);
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(REGISTER_POJO_WRONG_PHONE);
        assertThat(doesCourierEmailOrPhoneMatch).isFalse();
    }

    @Test
    void whenExistingPhoneNumberAndVehicle_ID_thenReturnTrue_exists(){
        RegisterCourierPojo rcp1 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L, 1L);
        when(courierRepository.findByEmail(rcp1.getEmail())).thenReturn(null);
        when(courierRepository.findByPhoneNumber(rcp1.getPhoneNumber())).thenReturn(c1);
        when(vehicleRepository.findById(rcp1.getVehicle_id())).thenReturn(java.util.Optional.ofNullable(v1));
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(REGISTER_POJO_WRONG_EMAIL);
        assertThat(doesCourierEmailOrPhoneMatch).isTrue();
    }

    @Test
    void whenExistingPhoneNumber_thenReturnTrue_exists(){
        RegisterCourierPojo rcp2 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 2L, 1L);
        when(courierRepository.findByEmail(rcp2.getEmail())).thenReturn(null);
        when(courierRepository.findByPhoneNumber(rcp2.getPhoneNumber())).thenReturn(c1);
        when(vehicleRepository.findById(Mockito.any())).thenReturn(null);//should be using hashes
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(REGISTER_POJO_WRONG_EMAIL);
        assertThat(doesCourierEmailOrPhoneMatch).isFalse();
    }

    @Test
    void whenNonExistingEmailOrPhoneNumber_thenReturnFalse_exists(){
        RegisterCourierPojo rcp1 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L, 1L);
        when(courierRepository.findByEmail(rcp1.getEmail())).thenReturn(null);
        when(courierRepository.findByPhoneNumber(rcp1.getPhoneNumber())).thenReturn(null);
        when(vehicleRepository.findById(rcp1.getVehicle_id())).thenReturn(java.util.Optional.ofNullable(v1));
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(REGISTER_POJO_WRONG_PHONE_AND_EMAIL);
        assertThat(doesCourierEmailOrPhoneMatch).isFalse();
    }

    @Test
    void whenNonExistingEmailOrPhoneNumberOrVehicle_ID_thenReturnFalse_exists(){
        RegisterCourierPojo rcp2 = new RegisterCourierPojo("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, 1L, 1L);
        when(courierRepository.findByEmail(rcp2.getEmail())).thenReturn(null);
        when(courierRepository.findByPhoneNumber(rcp2.getPhoneNumber())).thenReturn(null);
        when(vehicleRepository.findById(rcp2.getVehicle_id())).thenReturn(java.util.Optional.ofNullable(v1));
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(REGISTER_POJO_WRONG_PHONE_AND_EMAIL);
        assertThat(doesCourierEmailOrPhoneMatch).isFalse();
    }

    //verifyLogin

    @Test
    void whenMailAndPasswordCorrespond_ThenReturnCourier_verifyLogin(){
        when(courierRepository.findByEmail(Mockito.any())).thenReturn(c1);
        Courier res_courier = courierService.verifyLogin(lcp1);
        logger.log(Level.INFO, res_courier.toString());
        assertThat(lcp1.getEmail()).isEqualTo(c1.getEmail());
        assertThat(lcp1.getPassword()).isEqualTo(c1.getPassword());
    }
    @Test
    void whenMailCorrespondsButNotPassword_ThenReturnNull_verifyLogin(){
        when(courierRepository.findByEmail(lcp1.getEmail())).thenReturn(c1);
        Courier res_courier = courierService.verifyLogin(LOGIN_COURIER_POJO_WRONG_PASSWORD);
        assertThat(res_courier).isNull();
    }

    @Test
    void whenPasswordCorrespondsButNotEmail_ThenReturnNull_verifyLogin(){
        when(courierRepository.findByEmail(lcp1.getEmail())).thenReturn(null);
        Courier res_courier = courierService.verifyLogin(LOGIN_COURIER_POJO_WRONG_EMAIL);
        assertThat(res_courier).isNull();
    }

}
