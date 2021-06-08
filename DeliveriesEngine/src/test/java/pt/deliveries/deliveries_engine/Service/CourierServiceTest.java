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
import pt.deliveries.deliveries_engine.Repository.CourierRepository;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {

    private final Courier COURIER_WRONG_PASSWORD = new Courier("Marco Alves","marcoA@gmail.com","12345679","M",931231233L, new Supervisor("Carlos","12345678"), new Vehicle("car"));
    private final Courier COURIER_WRONG_EMAIL_AND_PHONE =  new Courier("Marco Alves","marcoAl@gmail.com","12345678","M",931231234L, new Supervisor("Carlos","12345678"), new Vehicle("car"));
    private final Courier COURIER_WRONG_EMAIL = new Courier("Marco Alves","marcoAl@gmail.com","12345678","M",931231233L, new Supervisor("Carlos","12345678"), new Vehicle("car"));
    private final Courier COURIER_WRONG_PHONE = new Courier("Marco Alves","marcoA@gmail.com","12345678","M",931231234L, new Supervisor("Carlos","12345678"), new Vehicle("car"));

    @Mock(lenient = true)
    private CourierRepository courierRepository;

    @InjectMocks
    private CourierServiceImpl courierService;

    private Courier c1;
    @BeforeEach
    public void setUp(){
        c1 = new Courier("Marco Alves","marcoA@gmail.com","12345678","M",931231233L, new Supervisor("Carlos","12345678"), new Vehicle("car"));
        //Courier c2 = new Courier("Renato","marcoA@gmail.com","12345678","M",931231233L, new Supervisor("Carlos","12345678"), new Vehicle("car"));
        Mockito.when(courierRepository.findByEmail(c1.getEmail())).thenReturn(c1);
        Mockito.when(courierRepository.findByPhoneNumber(c1.getPhoneNumber())).thenReturn(c1);
    }

    @Test
    void whenExistingEmailOrPhoneNumber_thenReturnTrue_exists(){
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(c1);
        assertThat(doesCourierEmailOrPhoneMatch).isTrue();
    }

    @Test
    void whenExistingEmail_thenReturnTrue_exists(){
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(COURIER_WRONG_PHONE);
        assertThat(doesCourierEmailOrPhoneMatch).isTrue();
    }

    @Test
    void whenExistingPhoneNumber_thenReturnTrue_exists(){
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(COURIER_WRONG_EMAIL);
        assertThat(doesCourierEmailOrPhoneMatch).isTrue();
    }
    @Test
    void whenNonExistingEmailOrPhoneNumber_thenReturnFalse_exists(){
        boolean doesCourierEmailOrPhoneMatch = courierService.exists(COURIER_WRONG_EMAIL_AND_PHONE);
        assertThat(doesCourierEmailOrPhoneMatch).isFalse();
    }

    @Test
    void whenMailAndPasswordCorrespond_ThenReturnCourier_verifyLogin(){
        Courier res_courier = courierService.verifyLogin(c1);
        assertThat(res_courier.getEmail()).isEqualTo(c1.getEmail());
        assertThat(res_courier.getPassword()).isEqualTo(c1.getPassword());
        assertThat(res_courier.getName()).isEqualTo(c1.getName());
    }
    @Test
    void whenMailCorrespondsButNotPassword_ThenReturnNull_verifyLogin(){
        Courier res_courier = courierService.verifyLogin(COURIER_WRONG_PASSWORD);
        assertThat(res_courier).isNull();
    }

    @Test
    void whenPasswordCorrespondsButNotEmail_ThenReturnNull_verifyLogin(){
        Courier res_courier = courierService.verifyLogin(COURIER_WRONG_PASSWORD);
        assertThat(res_courier).isNull();
    }

}
