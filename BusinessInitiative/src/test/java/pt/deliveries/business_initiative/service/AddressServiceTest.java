package pt.deliveries.business_initiative.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Client;
import pt.deliveries.business_initiative.pojo.AddressSaveForClientPOJO;
import pt.deliveries.business_initiative.repository.AddressRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock(lenient = true)
    private AddressRepository repository;

    @Mock(lenient = true)
    private ClientServiceImpl clientService;

    @InjectMocks private AddressServiceImpl service;


    @BeforeEach
    public void setUp() {
        Address address1 = new Address("city1", "address1", 10, 11);
        Address address2 = new Address("city2", "address2", 10, 11);
        Address address3 = new Address("city3", "address3", 10, 11);
        address1.setId(1L);
        address2.setId(2L);
        address3.setId(3L);


        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        Client goodClient2 = new Client("cunha2", "cunha@ua.pt", "1234", null, "M", "96000002");
        Client goodClient3 = new Client("cunha3", "cunha@ua.pt", "1234", null, "M", "96000003");
        goodClient1.setId(1L);
        goodClient2.setId(2L);
        goodClient3.setId(3L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));
        goodClient2.setAddresses(new HashSet<>(Collections.singleton(address2)));

        Set<Address> goodClient3Adresses = new HashSet<>();
        goodClient3Adresses.add(address2);
        goodClient3Adresses.add(address3);

        //goodClient3.setAddresses(new HashSet<>(Arrays.asList(address2, address3)));
        goodClient3.setAddresses(goodClient3Adresses);

        List<Address> allAddresses = new ArrayList<>(Arrays.asList(address1, address2, address3));

        when( repository.findAll() ).thenReturn( allAddresses );
        when( clientService.findById(goodClient3.getId()) ).thenReturn(goodClient3);
        when( clientService.save( Mockito.any()) ).thenReturn( goodClient3 );
    }


    @Test
    void given3Addresses_whenFindAllAddresses_thenReturnAllAddresses() {
        Address address1 = new Address("city1", "address1", 10, 11);
        Address address2 = new Address("city2", "address2", 10, 11);
        Address address3 = new Address("city3", "address3", 10, 11);
        address1.setId(1L);
        address2.setId(2L);
        address3.setId(3L);

        List<Address> allAddresses = new ArrayList<>(Arrays.asList(address1, address2, address3));

        List<Address> addressesFound = service.findAllAddresses();

        verifyFindAllIsCalledOnce();
        assertThat(addressesFound).hasSize(3).extracting(Address::getId).contains(allAddresses.get(0).getId(), allAddresses.get(1).getId(), allAddresses.get(2).getId());
        assertThat(addressesFound).hasSize(3).extracting(Address::getCity).contains(allAddresses.get(0).getCity(), allAddresses.get(1).getCity(), allAddresses.get(2).getCity());
        assertThat(addressesFound).hasSize(3).extracting(Address::getLatitude).contains(allAddresses.get(0).getLatitude(), allAddresses.get(1).getLatitude(), allAddresses.get(2).getLatitude());
        assertThat(addressesFound).hasSize(3).extracting(Address::getLongitude).contains(allAddresses.get(0).getLongitude(), allAddresses.get(1).getLongitude(), allAddresses.get(2).getLongitude());
    }


    @Test
    void whenAssociateAddressToClient_thenClientShouldBeUpdated() {
        AddressSaveForClientPOJO address2POJO = new AddressSaveForClientPOJO("city2", "address2", 10, 11);

        Address address2 = new Address("city2", "address2", 10, 11);
        address2.setId(2L);

        Client goodClient3 = new Client("cunha3", "cunha@ua.pt", "1234", null, "M", "96000003");
        goodClient3.setId(3L);
        goodClient3.setAddresses(new HashSet<>(Collections.singleton(address2)));


        Client saved = service.saveClientAddress(address2POJO, goodClient3.getId());


        assertThat(saved.getId()).isEqualTo(goodClient3.getId());
        assertThat(saved.getName()).isEqualTo(saved.getName());
        assertThat(saved.getEmail()).isEqualTo(saved.getEmail());
        assertThat(saved.getPassword()).isEqualTo(saved.getPassword());
        assertThat(saved.getGender()).isEqualTo(saved.getGender());
        assertThat(saved.getPhoneNumber()).isEqualTo(saved.getPhoneNumber());
        assertThat(saved.getAddresses().iterator().next()).isEqualTo(goodClient3.getAddresses().iterator().next());
        assertThat(saved.getAddresses().iterator().next()).isEqualTo(goodClient3.getAddresses().iterator().next());

        verifySaveClientAddressIsCalledOnce(goodClient3);
    }


    private void verifyFindAllIsCalledOnce() {
        verify(repository, times(1)).findAll();
    }

    private void verifySaveClientAddressIsCalledOnce(Client client) {
        verify(clientService, times(1)).save(Mockito.any());
    }

}

