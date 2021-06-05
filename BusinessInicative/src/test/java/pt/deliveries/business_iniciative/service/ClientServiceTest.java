package pt.deliveries.business_iniciative.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.repository.ClientRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock( lenient = true )
    private ClientRepository repository;

    @InjectMocks
    private ClientServiceImpl service;


    // Verify Register
    @Test
    public void whenValidEmailAndPhoneNumber_thenReturnTrue() {
        Client validClient = new Client(1L, "bob", "bob@ua.pt", "1234", "address", "M", "96000000");

        Boolean valid = service.verifyRegister(validClient);
        assertThat(valid).isEqualTo(true);
        verifyFindByEmailIsCalledOnce(validClient.getEmail());
        verifyFindByPhoneNumberIsCalledOnce(validClient.getPhoneNumber());
    }

    @Test
    public void whenValidEmailAndInvalidPhoneNumber_thenReturnFalse() {
        Client validClient = new Client(1L, "bob", "bob@ua.pt", "1234", "address", "M", "96000000");
        Client invalidClient = new Client(1L, "bob", "bob@ua.pt", "1234", "address", "M", "---");

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(null);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(validClient);

        Boolean valid = service.verifyRegister(invalidClient);
        assertThat(valid).isEqualTo(false);
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
        verifyFindByPhoneNumberIsCalledOnce(invalidClient.getPhoneNumber());
    }

    @Test
    public void whenInvalidEmailAndValidPhoneNumber_thenReturnFalse() {
        Client validClient = new Client(1L, "bob", "bob@ua.pt", "1234", "address", "M", "96000000");
        Client invalidClient = new Client(1L, "bob", "---", "1234", "address", "M", "96000000");

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(validClient);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(null);

        Boolean valid = service.verifyRegister(invalidClient);
        assertThat(valid).isEqualTo(false);
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
    }

    @Test
    public void whenInvalidEmailAndInvalidPhoneNumber_thenReturnFalse() {
        Client validClient = new Client(1L, "bob", "bob@ua.pt", "1234", "address", "M", "96000000");
        Client invalidClient = new Client(1L, "bob", "---", "1234", "address", "M", "---");

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(validClient);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(validClient);

        Boolean valid = service.verifyRegister(invalidClient);
        assertThat(valid).isEqualTo(false);
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
    }


    // Verify login
    @Test
    public void whenValidEmailAndPassword_thenReturnClient() {
        Client validClient = new Client(1L, "bob", "bob@ua.pt", "1234", "address", "M", "96000000");

        when(repository.findByEmail(validClient.getEmail())).thenReturn(validClient);

        Client found = service.verifyLogin(validClient);
        assertThat(found.getName()).isEqualTo(validClient.getName());
        verifyFindByEmailIsCalledOnce(validClient.getEmail());
    }

    @Test
    public void whenValidEmailAndInvalidPassword_thenReturnNull() {
        Client validClient = new Client(1L, "bob", "bob@ua.pt", "1234", "address", "M", "96000000");
        Client nonClient = new Client(1L, "bob", "bob@ua.pt", "---", "address", "M", "96000000");

        when(repository.findByEmail(nonClient.getEmail())).thenReturn(validClient);

        Client notFound = service.verifyLogin(nonClient);
        assertThat(notFound).isEqualTo(null);
        verifyFindByEmailIsCalledOnce(nonClient.getEmail());
    }

    @Test
    public void whenInValidEmailAndValidPassword_thenReturnNull() {
        Client nonClient = new Client(1L, "bob", "---", "1234", "address", "M", "96000000");

        when(repository.findByEmail(nonClient.getEmail())).thenReturn(null);

        Client notFound = service.verifyLogin(nonClient);
        assertThat(notFound).isEqualTo(null);
        verifyFindByEmailIsCalledOnce(nonClient.getEmail());
    }

    @Test
    public void whenInValidEmailAndInvalidPassword_thenReturnNull() {
        Client nonClient = new Client(1L, "bob", "---", "1234", "address", "M", "96000000");

        when(repository.findByEmail(nonClient.getEmail())).thenReturn(null);

        Client notFound = service.verifyLogin(nonClient);
        assertThat(notFound).isEqualTo(null);
        verifyFindByEmailIsCalledOnce(nonClient.getEmail());
    }





    private void verifyFindByEmailIsCalledOnce(String email) {
        verify(repository, times(1)).findByEmail(email);
    }

    private void verifyFindByPhoneNumberIsCalledOnce(String phoneNumber) {
        verify(repository, times(1)).findByPhoneNumber(phoneNumber);
    }

}
