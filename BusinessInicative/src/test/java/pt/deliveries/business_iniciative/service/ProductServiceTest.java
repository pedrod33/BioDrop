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
import pt.deliveries.business_iniciative.repository.ProductRepository;
import pt.deliveries.business_iniciative.repository.StoreRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock(lenient = true)
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @InjectMocks
    private StoreServiceImpl storeService;



    @BeforeEach
    public void setUp() {
        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        Product prod3 = new Product("prod3", "origin3", 10, "path", 100 );
        prod1.setId(1L);
        prod2.setId(2L);
        prod3.setId(3L);

        List<Product> allProducts = Arrays.asList(prod1, prod2, prod3);

        when(repository.findAll()).thenReturn(allProducts);
        when(repository.findById(prod1.getId())).thenReturn(Optional.of(prod1));
        when(repository.findById(0L)).thenReturn(null);
    }



    @Test
    public void given3Products_whenFindAllProducts_thenReturnAllProducts() {
        Product prod1 = new Product("prod1", "origin1", 10, "path", 100);
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100);
        Product prod3 = new Product("prod3", "origin3", 10, "path", 100);
        prod1.setId(1L);
        prod2.setId(2L);
        prod3.setId(3L);

        List<Product> allProducts = service.findAllProducts();

        verifyFindAllIsCalledOnce();
        assertThat(allProducts).hasSize(3).extracting(Product::getName).contains(prod1.getName(), prod2.getName(), prod3.getName());
    }


    @Test
    public void whenValidId_thenProductShouldBeFound() {
        Long prodId = 1L;
        Product found = service.findById(prodId);

        assertThat(found.getId()).isEqualTo(prodId);
        verifyFindByIdIsCalledOnce(prodId);
    }


    //@Test
    public void whenValidProduct_thenProductShouldBeCreated() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);
        Set<Product> productsInStore = new HashSet<>();
        productsInStore.add(prod1);
        store.setProducts(productsInStore);

        System.out.println(store.getId());

        when(storeService.findById(store.getId())).thenReturn(store);
        when(storeService.saveStore(store)).thenReturn(store);


        Store store_found = storeService.findById(store.getId());

        System.out.println(store_found);
        //assertThat(store_found.getId()).isEqualTo(store.getId());
        //assertThat(store_found.getProducts()).isEqualTo(productsInStore);

        //verifyFindByIdIsCalledOnce(store.getId());
    }



    private void verifyFindAllIsCalledOnce() {
        verify(repository, times(1)).findAll();
    }

    private void verifyFindByIdIsCalledOnce(Long prodId) {
        verify(repository, times(1)).findById(prodId);
    }


}