package pt.deliveries.business_iniciative.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_iniciative.model.Address;
import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;
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
        Address address1 = new Address("city1", "address1", 10, 11);
        Address address2 = new Address("city2", "address2", 10, 11);
        Address address3 = new Address("city3", "address3", 10, 11);
        address1.setId(1L);
        address2.setId(2L);
        address3.setId(3L);

        Set<Product> products = new HashSet<>();
        Store store1 = new Store("store1", null, products);
        Store store2 = new Store("store2", null, products);
        Store store3 = new Store("store3", null, products);
        store1.setId(1L);
        store2.setId(2L);
        store3.setId(3L);
        store1.setAddress(address1);
        store2.setAddress(address2);
        store3.setAddress(address3);


        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        Product prod3 = new Product("prod3", "origin3", 10, "path", 100 );
        prod1.setId(1L);
        prod2.setId(2L);
        prod3.setId(3L);

        Set<Product> productsInStore = new HashSet<>();
        productsInStore.add(prod1);
        productsInStore.add(prod2);
        productsInStore.add(prod3);

        store1.setProducts(productsInStore);


        List<Store> allStores = Arrays.asList(store1, store2, store3);

        when(repository.findAll()).thenReturn(allStores);
        when(repository.findByName(store1.getName())).thenReturn(Collections.singletonList(store1));
        when(repository.findByName("wrong_name")).thenReturn(null);
        when(repository.findByAddress_Address(store1.getAddress().getAddress())).thenReturn(Collections.singletonList(store1));
        when(repository.findByAddress_Address("wrong_name")).thenReturn(null);
        when(repository.findByAddress_LatitudeAndAddress_Longitude(store1.getAddress().getLatitude(), store1.getAddress().getLongitude())).thenReturn(store1);
        when(repository.findByAddress_LatitudeAndAddress_Longitude(0, 0)).thenReturn(null);
        when(repository.findById(store1.getId())).thenReturn(Optional.of(store1));
    }

    @Test
    public void given3Stores_whenFindAllStores_thenReturnAllStores() {
        Address address1 = new Address("city1", "address1", 10, 11);
        Address address2 = new Address("city2", "address2", 10, 11);
        Address address3 = new Address("city3", "address3", 10, 11);
        address1.setId(1L);
        address2.setId(2L);
        address3.setId(3L);


        Set<Product> products = new HashSet<>();
        Store store1 = new Store("store1", null, products);
        Store store2 = new Store("store2", null, products);
        Store store3 = new Store("store3", null, products);
        store1.setAddress(address1);
        store2.setAddress(address2);
        store3.setAddress(address3);

        List<Store> allStores = service.findAllStores();

        verifyFindAllIsCalledOnce();
        assertThat(allStores).hasSize(3).extracting(Store::getName).contains(store1.getName(), store2.getName(), store3.getName());
    }

    @Test
    public void whenValidId_thenStoreShouldBeFound() {
        Long storeId = 1L;
        Store found = service.findById(storeId);

        assertThat(found.getId()).isEqualTo(storeId);
        verifyFindByIdIsCalledOnce(storeId);
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

        assertThat(found.get(0).getAddress().getAddress()).isEqualTo(address);
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
        double lat = 10;
        double lng = 11;
        Store found = service.findByLatAndLng(lat, lng);

        assertThat(found.getAddress().getLatitude()).isEqualTo(lat);
        assertThat(found.getAddress().getLongitude()).isEqualTo(lng);
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

    @Test
    public void given3ProductsInOneStore_whenFindAllProductsInStore_withValidId_thenReturnAllProductsInStore() {
        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        Product prod3 = new Product("prod3", "origin3", 10, "path", 100 );
        prod1.setId(1L);
        prod2.setId(2L);
        prod3.setId(3L);

        Set<Product> productsInStore = new HashSet<>();
        productsInStore.add(prod1);
        productsInStore.add(prod2);
        productsInStore.add(prod3);

        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Store store1 = new Store("store1", null, null);
        store1.setId(1L);
        store1.setAddress(address1);
        store1.setProducts(productsInStore);


        Set<Product> productsInStore1 = service.findAllProductsInStore(store1.getId());


        verifyFindByIdIsCalledOnce(store1.getId());
        assertThat(productsInStore1).isNotNull();
        assertThat(productsInStore1).hasSize(3).extracting(Product::getName).contains(prod1.getName(), prod2.getName(), prod3.getName());
    }

    @Test
    public void whenFindAllProductsInStore_withInvalidId_thenReturnAllProductsInStore() {

        when(repository.findById(0L)).thenReturn(Optional.empty());

        Store notFound = repository.findById(0L).orElse(null);

        verifyFindByIdIsCalledOnce(0L);
        assertThat(notFound).isNull();
    }

    @Test
    public void whenSaveValidStore_thenStoreShouldBeSaved() {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Store store1 = new Store("store1", null, null);
        store1.setId(1L);
        store1.setAddress(address1);


        when(repository.save(store1)).thenReturn(store1);
        Store saved = service.saveStore(store1);


        assertThat(saved.getId()).isEqualTo(store1.getId());
        assertThat(saved.getName()).isEqualTo(store1.getName());
        assertThat(saved.getAddress()).isEqualTo(store1.getAddress());

        verifySaveIsCalledOnce(store1);
    }


    private void verifyFindAllIsCalledOnce() {
        verify(repository, times(1)).findAll();
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        verify(repository, times(1)).findByName(name);
    }

    private void verifyFindByAddressIsCalledOnce(String address) {
        verify(repository, times(1)).findByAddress_Address(address);
    }

    private void verifyFindByLatAndLngIsCalledOnce(double lat, double lng) {
        verify(repository, times(1)).findByAddress_LatitudeAndAddress_Longitude(lat, lng);
    }

    private void verifyFindByIdIsCalledOnce(Long storeId) {
        verify(repository, times(1)).findById(storeId);
    }

    private void verifySaveIsCalledOnce(Store store) {
        verify(repository, times(1)).save(store);
    }

}