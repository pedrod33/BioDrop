package pt.deliveries.business_iniciative.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_iniciative.model.Address;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.repository.ClientRepository;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
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
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);
        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        validClient.setId(1L);
        validClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        Boolean valid = service.verifyRegister(validClient);
        assertThat(valid).isEqualTo(true);
        verifyFindByEmailIsCalledOnce(validClient.getEmail());
        verifyFindByPhoneNumberIsCalledOnce(validClient.getPhoneNumber());
    }

    @Test
    public void whenValidEmailAndInvalidPhoneNumber_thenReturnFalse() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        validClient.setId(1L);
        validClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        Client invalidClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "---");
        invalidClient.setId(2L);
        invalidClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(null);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(validClient);

        Boolean valid = service.verifyRegister(invalidClient);
        assertThat(valid).isEqualTo(false);
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
        verifyFindByPhoneNumberIsCalledOnce(invalidClient.getPhoneNumber());
    }

    @Test
    public void whenInvalidEmailAndValidPhoneNumber_thenReturnFalse() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        validClient.setId(1L);
        validClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        Client invalidClient = new Client("bob", "---", "1234", null, "M", "96000000");
        invalidClient.setId(2L);
        invalidClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(validClient);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(null);

        Boolean valid = service.verifyRegister(invalidClient);
        assertThat(valid).isEqualTo(false);
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
    }

    @Test
    public void whenInvalidEmailAndInvalidPhoneNumber_thenReturnFalse() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        validClient.setId(1L);
        validClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        Client invalidClient = new Client("bob", "---", "1234", null, "M", "---");
        invalidClient.setId(2L);
        invalidClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(validClient);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(validClient);

        Boolean valid = service.verifyRegister(invalidClient);
        assertThat(valid).isEqualTo(false);
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
    }


    // Verify login
    @Test
    public void whenValidEmailAndPassword_thenReturnClient() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        validClient.setId(1L);
        validClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        when(repository.findByEmail(validClient.getEmail())).thenReturn(validClient);

        Client found = service.verifyLogin(validClient);
        assertThat(found.getName()).isEqualTo(validClient.getName());
        verifyFindByEmailIsCalledOnce(validClient.getEmail());
    }

    @Test
    public void whenValidEmailAndInvalidPassword_thenReturnNull() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        validClient.setId(1L);
        validClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        Client nonClient = new Client("bob", "bob@ua.pt", "---", null, "M", "96000000");
        nonClient.setId(2L);
        nonClient.setAddresses(new HashSet<>(Collections.singletonList(address)));


        when(repository.findByEmail(nonClient.getEmail())).thenReturn(validClient);

        Client notFound = service.verifyLogin(nonClient);
        assertThat(notFound).isEqualTo(null);
        verifyFindByEmailIsCalledOnce(nonClient.getEmail());
    }

    @Test
    public void whenInValidEmailAndValidPassword_thenReturnNull() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Client nonClient = new Client("bob", "---", "1234", null, "M", "96000000");
        nonClient.setId(1L);
        nonClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

        when(repository.findByEmail(nonClient.getEmail())).thenReturn(null);

        Client notFound = service.verifyLogin(nonClient);
        assertThat(notFound).isEqualTo(null);
        verifyFindByEmailIsCalledOnce(nonClient.getEmail());
    }

    @Test
    public void whenInValidEmailAndInvalidPassword_thenReturnNull() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Client nonClient = new Client("bob", "---", "1234", null, "M", "96000000");
        nonClient.setId(1L);
        nonClient.setAddresses(new HashSet<>(Collections.singletonList(address)));

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
