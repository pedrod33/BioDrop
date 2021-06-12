package pt.deliveries.deliveries_engine.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.deliveries_engine.Model.Supervisor;
import pt.deliveries.deliveries_engine.Pojo.RegisterSupervisorPojo;
import pt.deliveries.deliveries_engine.Repository.SupervisorRepository;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupervisorServiceTest {

    @Mock(lenient = true)
    private SupervisorRepository supervisorRepository;

    @InjectMocks
    private SupervisorServiceImpl supervisorService;

    Supervisor s1;
    RegisterSupervisorPojo rsp1 = new RegisterSupervisorPojo("supervisor1@gmail.com","12345678","Manuel Torres");
    RegisterSupervisorPojo rsp2 = new RegisterSupervisorPojo("supervisor@gmail.com","12345678","Manuel Torres");
    @BeforeEach
    public void setUp(){
        s1 = new Supervisor("supervisor@gmail.com", "12345678", "Carlos Silva");
        when(supervisorRepository. findByEmail(s1.getEmail())).thenReturn(s1);
    }

    @Test
    public void givenSupervisorPojo_create(){
        Supervisor s2 = new Supervisor();
        s2.setPassword(rsp1.getPassword());
        s2.setName(rsp1.getName());
        s2.setEmail(rsp1.getEmail());
        when(supervisorRepository.save(s2)).thenReturn(s2);
        Supervisor created = supervisorRepository.save(s2);
        created.getEmail().equals(rsp1.getEmail());
        created.getPassword().equals(rsp1.getPassword());
        created.getName().equals(rsp1.getName());
    }

    @Test
    public void givenPojoEmailAlreadyExists_returnTrue_existsRegister(){
        boolean doesSupervisorExist = supervisorService.existsRegister(rsp2);
        assertThat(doesSupervisorExist).isTrue();
    }

    @Test
    public void givenPojoEmailDoesNotExist_returnFalse_existsRegister(){
        boolean doesSupervisorExist = supervisorService.existsRegister(rsp1);
        assertThat(doesSupervisorExist).isFalse();
    }
}
