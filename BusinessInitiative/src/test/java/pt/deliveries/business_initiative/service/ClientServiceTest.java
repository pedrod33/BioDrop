package pt.deliveries.business_initiative.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_initiative.exception.ClientEmailOrPasswordIncorrectException;
import pt.deliveries.business_initiative.exception.ClientEmailOrPhoneNumberInUseException;
import pt.deliveries.business_initiative.exception.ClientNotFoundException;
import pt.deliveries.business_initiative.model.*;
import pt.deliveries.business_initiative.pojo.ClientLoginPOJO;
import pt.deliveries.business_initiative.pojo.ClientRegistrationPOJO;
import pt.deliveries.business_initiative.pojo.StoreSavePOJO;
import pt.deliveries.business_initiative.repository.ClientRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock( lenient = true )
    private ClientRepository repository;

    @InjectMocks
    private ClientServiceImpl service;

    // Find All
    @Test
    void given3Clients_whenFindAllClients_thenReturnAllClients() {
        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        Client goodClient2 = new Client("cunha2", "cunha@ua.pt", "1234", null, "M", "96000002");
        Client goodClient3 = new Client("cunha3", "cunha@ua.pt", "1234", null, "M", "96000003");
        goodClient1.setId(1L);
        goodClient2.setId(2L);
        goodClient3.setId(3L);

        List<Client> expectedClients = new ArrayList<>(Arrays.asList(goodClient1, goodClient2, goodClient3));
        when(repository.findAll()).thenReturn(expectedClients);

        List<Client> allClients = service.findAll();

        verifyFindByFindAllIsCalledOnce();
        assertThat(allClients).hasSize(3).extracting(Client::getId).contains(1L, 2L, 3L);
        assertThat(allClients).hasSize(3).extracting(Client::getName).contains("cunha1", "cunha2", "cunha3");
    }


    // Verify Register
    @Test
    public void whenValidEmailAndPhoneNumber_thenReturnTrue() {
        ClientRegistrationPOJO validClient = new ClientRegistrationPOJO("bob", "bob@ua.pt", "1234", "M", "96000000");
        service.verifyRegister(validClient);

        verifyFindByEmailIsCalledOnce(validClient.getEmail());
        verifyFindByPhoneNumberIsCalledOnce(validClient.getPhoneNumber());
    }

    @Test
    public void whenValidEmailAndInvalidPhoneNumber_thenReturnFalse() {
        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        ClientRegistrationPOJO invalidClient = new ClientRegistrationPOJO("bob", "bob@ua.pt", "1234", "M", "---");

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(null);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(validClient);

        assertThrows(ClientEmailOrPhoneNumberInUseException.class, () -> service.verifyRegister(invalidClient));
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
        verifyFindByPhoneNumberIsCalledOnce(invalidClient.getPhoneNumber());
    }

    @Test
    public void whenInvalidEmailAndValidPhoneNumber_thenReturnFalse() {
        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        ClientRegistrationPOJO invalidClient = new ClientRegistrationPOJO("bob", "---", "1234", "M", "96000000");

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(validClient);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(null);

        assertThrows(ClientEmailOrPhoneNumberInUseException.class, () -> service.verifyRegister(invalidClient));
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
    }

    @Test
    public void whenInvalidEmailAndInvalidPhoneNumber_thenReturnFalse() {
        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");

        ClientRegistrationPOJO invalidClient = new ClientRegistrationPOJO("bob", "---", "1234", "M", "---");

        when(repository.findByEmail(invalidClient.getEmail())).thenReturn(validClient);
        when(repository.findByPhoneNumber(invalidClient.getPhoneNumber())).thenReturn(validClient);

        assertThrows(ClientEmailOrPhoneNumberInUseException.class, () -> service.verifyRegister(invalidClient));
        verifyFindByEmailIsCalledOnce(invalidClient.getEmail());
    }

    // Register Client
    @Test
    public void whenRegisterValidClient_thenClientShouldBeSaved() {
        Client validClient = new Client("bob", "bob@ua.pt", "1234", new HashSet<>(), "M", "96000000");
        ClientRegistrationPOJO validClientPOJO = new ClientRegistrationPOJO("bob", "bob@ua.pt", "1234", "M", "96000000");


        when(repository.save(validClient)).thenReturn(validClient);
        Client saved = service.registerClient(validClientPOJO);


        assertThat(saved.getName()).isEqualTo(validClientPOJO.getName());
        assertThat(saved.getPassword()).isEqualTo(validClientPOJO.getPassword());
        assertThat(saved.getEmail()).isEqualTo(validClientPOJO.getEmail());
        assertThat(saved.getPhoneNumber()).isEqualTo(validClientPOJO.getPhoneNumber());

        verifySaveIsCalledOnce(validClient);
    }


    // Verify login
    @Test
    public void whenValidEmailAndPassword_thenReturnClient() {
        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        ClientLoginPOJO validClientPOJO = new ClientLoginPOJO("bob@ua.pt", "1234");

        when(repository.findByEmail(validClient.getEmail())).thenReturn(validClient);

        Client found = service.verifyLogin(validClientPOJO);
        assertThat(found.getName()).isEqualTo(validClient.getName());
        verifyFindByEmailIsCalledOnce(validClient.getEmail());
    }

    @Test
    public void whenValidEmailAndInvalidPassword_thenReturnNull() {
        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        Client nonClient = new Client("bob", "bob@ua.pt", "---", null, "M", "96000000");
        ClientLoginPOJO nonClientPOJO = new ClientLoginPOJO("bob@ua.pt", "---");

        when(repository.findByEmail(nonClient.getEmail())).thenReturn(validClient);

        assertThrows(ClientEmailOrPasswordIncorrectException.class, () -> service.verifyLogin(nonClientPOJO));
        verifyFindByEmailIsCalledOnce(nonClient.getEmail());
    }

    @Test
    public void whenInValidEmailAndValidPassword_thenReturnNull() {
        ClientLoginPOJO nonClientPOJO = new ClientLoginPOJO("---", "1234");


        when(repository.findByEmail(nonClientPOJO.getEmail())).thenReturn(null);

        assertThrows(ClientEmailOrPasswordIncorrectException.class, () -> service.verifyLogin(nonClientPOJO));
        verifyFindByEmailIsCalledOnce(nonClientPOJO.getEmail());
    }

    @Test
    public void whenInValidEmailAndInvalidPassword_thenReturnNull() {
        ClientLoginPOJO nonClientPOJO = new ClientLoginPOJO("---", "1234");

        when(repository.findByEmail(nonClientPOJO.getEmail())).thenReturn(null);

        assertThrows(ClientEmailOrPasswordIncorrectException.class, () -> service.verifyLogin(nonClientPOJO));
        verifyFindByEmailIsCalledOnce(nonClientPOJO.getEmail());
    }


    // Find by Id
    @Test
    public void whenValidId_thenClientShouldBeFound() {
        Client validClient = new Client("bob", "bob@ua.pt", "1234", null, "M", "96000000");
        validClient.setId(1L);

        Long clientId = 1L;
        when(repository.findById(clientId)).thenReturn(Optional.of(validClient));

        Client found = service.findById(clientId);
        assertThat(found.getId()).isEqualTo(clientId);
        verifyFindByIdIsCalledOnce(clientId);
    }

    @Test
    public void whenValidInvalidId_throwClientNotFound() {
        Long clientId = 0L;
        when(repository.findById(clientId)).thenThrow(ClientNotFoundException.class);

        assertThrows(ClientNotFoundException.class, () -> { service.findById(clientId); });
        verifyFindByIdIsCalledOnce(clientId);
    }


    private void verifyFindByFindAllIsCalledOnce() {
        verify(repository, times(1)).findAll();
    }

    private void verifyFindByEmailIsCalledOnce(String email) {
        verify(repository, times(1)).findByEmail(email);
    }

    private void verifyFindByPhoneNumberIsCalledOnce(String phoneNumber) {
        verify(repository, times(1)).findByPhoneNumber(phoneNumber);
    }

    private void verifyFindByIdIsCalledOnce(Long clientId) {
        verify(repository, times(1)).findById(clientId);
    }

    private void verifySaveIsCalledOnce(Client client) {
        verify(repository, times(1)).save(client);
    }

}
