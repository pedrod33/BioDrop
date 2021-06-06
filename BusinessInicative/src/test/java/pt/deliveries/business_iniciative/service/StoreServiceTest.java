package pt.deliveries.business_iniciative.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;
import pt.deliveries.business_iniciative.repository.ClientRepository;
import pt.deliveries.business_iniciative.repository.StoreRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock(lenient = true)
    private StoreRepository repository;

    @InjectMocks
    private StoreServiceImpl service;

    @BeforeEach
    public void setUp() {
        Set<Product> products = new HashSet<>();
        Store store1 = new Store("store1", "address1", products, 100, 101);
        Store store2 = new Store("store2", "address2", products, 200, 201);
        Store store3 = new Store("store3", "address3", products, 300, 301);


        List<Store> allStores = Arrays.asList(store1, store2, store3);

        when(repository.findAll()).thenReturn(allStores);
        when(repository.findByName(store1.getName())).thenReturn(Collections.singletonList(store1));
        when(repository.findByName("wrong_name")).thenReturn(null);
        when(repository.findByAddress(store1.getAddress())).thenReturn(Collections.singletonList(store1));
        when(repository.findByAddress("wrong_name")).thenReturn(null);
        when(repository.findByLatitudeAndLongitude(store1.getLatitude(), store1.getLongitude())).thenReturn(store1);
        when(repository.findByLatitudeAndLongitude(0, 0)).thenReturn(null);
    }

    @Test
    public void given3Stores_whenFindAllStores_thenReturnAllStores() {
        Set<Product> products = new HashSet<>();
        Store store1 = new Store("store1", "address1", products, 100, 101);
        Store store2 = new Store("store2", "address2", products, 200, 201);
        Store store3 = new Store("store3", "address3", products, 300, 301);

        List<Store> allStores = service.findAllStores();

        verifyFindAllIsCalledOnce();
        assertThat(allStores).hasSize(3).extracting(Store::getName).contains(store1.getName(), store2.getName(), store3.getName());
    }

    @Test
    public void whenValidName_thenStoreShouldBeFound() {
        String name = "store1";
        List<Store> found = service.findByName(name);

        assertThat(found.get(0).getName()).isEqualTo(name);
        verifyFindByNameIsCalledOnce(name);
    }

    @Test
    public void whenInvalidName_thenStoreShouldNotBeFound() {
        String name = "wrong_name";
        List<Store> notFound = service.findByName(name);

        assertThat(notFound).isEqualTo(null);
        verifyFindByNameIsCalledOnce(name);
    }

    @Test
    public void whenValidAddress_thenStoreShouldBeFound() {
        String address = "address1";
        List<Store> found = service.findByAddress(address);

        assertThat(found.get(0).getAddress()).isEqualTo(address);
        verifyFindByAddressIsCalledOnce(address);
    }

    @Test
    public void whenInvalidAddress_thenStoreShouldNotBeFound() {
        String address = "wrong_address";
        List<Store> notFound = service.findByAddress(address);

        assertThat(notFound).isEmpty();
        verifyFindByAddressIsCalledOnce(address);
    }

    @Test
    public void whenValidCoords_thenStoreShouldBeFound() {
        double lat = 100;
        double lng = 101;
        Store found = service.findByLatAndLng(lat, lng);

        assertThat(found.getLatitude()).isEqualTo(lat);
        assertThat(found.getLongitude()).isEqualTo(lng);
        verifyFindByLatAndLngIsCalledOnce(lat, lng);
    }

    @Test
    public void whenInvalidCoords_thenStoreShouldNotBeFound() {
        double lat = 0;
        double lng = 0;
        Store notFound = service.findByLatAndLng(lat, lng);

        assertThat(notFound).isEqualTo(null);
        verifyFindByLatAndLngIsCalledOnce(lat, lng);
    }


    private void verifyFindAllIsCalledOnce() {
        verify(repository, times(1)).findAll();
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        verify(repository, times(1)).findByName(name);
    }

    private void verifyFindByAddressIsCalledOnce(String address) {
        verify(repository, times(1)).findByAddress(address);
    }

    private void verifyFindByLatAndLngIsCalledOnce(double lat, double lng) {
        verify(repository, times(1)).findByLatitudeAndLongitude(lat, lng);
    }
}