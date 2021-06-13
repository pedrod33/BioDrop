package pt.deliveries.business_initiative.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_initiative.exception.ClientEmailOrPasswordIncorrectException;
import pt.deliveries.business_initiative.exception.ClientNotFoundException;
import pt.deliveries.business_initiative.exception.ProductNotFoundException;
import pt.deliveries.business_initiative.exception.StoreNotFoundException;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Product;
import pt.deliveries.business_initiative.model.Store;
import pt.deliveries.business_initiative.pojo.SaveProductInStorePOJO;
import pt.deliveries.business_initiative.repository.ProductRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock(lenient = true)
    private ProductRepository repository;

    @Mock(lenient = true)
    private StoreServiceImpl storeService;

    @InjectMocks
    private ProductServiceImpl service;


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
        when(repository.findById(0L)).thenThrow(ProductNotFoundException.class);
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
        verifyProdRepFindByIdIsCalledOnce(prodId);
    }

    @Test
    public void whenValidInvalidId_throwClientNotFound() {
        Long prodId = 0L;
        when(repository.findById(prodId)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> { service.findById(prodId); });
        verifyProdRepFindByIdIsCalledOnce(prodId);
    }


    @Test
    public void whenValidProduct_withValidStoreId_thenProductShouldBeCreated() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);
        Store store = new Store("store1", null, null);
        store.setId(1L);
        store.setAddress(address);


        SaveProductInStorePOJO prod1POJO = new SaveProductInStorePOJO("prod1", "origin1", 10, "path", 100 );

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);
        Set<Product> productsInStore = new HashSet<>();
        productsInStore.add(prod1);


        when(storeService.findById(store.getId())).thenReturn(store);
        if (store.getProducts() != null) store.getProducts().add(prod1);
        else {
            store.setProducts(productsInStore);
        }
        when(storeService.save(store)).thenReturn(store);


        Store updatedStore = service.saveProd(prod1POJO, store.getId());


        assertThat(updatedStore.getId()).isEqualTo(store.getId());
        assertThat(updatedStore.getProducts()).isEqualTo(productsInStore);
        verifyFindByIdIsCalledOnce(store.getId());
    }

    @Test
    public void whenValidProduct_withValidStoreId_thenProductShouldBeAdded() {
        Address address = new Address("city", "address", 10, 11);
        address.setId(1L);

        SaveProductInStorePOJO prod1POJO = new SaveProductInStorePOJO("prod1", "origin1", 10, "path", 100 );


        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);
        Set<Product> productsInStore = new HashSet<>();
        productsInStore.add(prod1);

        Store store = new Store("store1", null, productsInStore);
        store.setId(1L);
        store.setAddress(address);


        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        prod2.setId(2L);


        when(storeService.findById(store.getId())).thenReturn(store);
        if (store.getProducts() != null) store.getProducts().add(prod2);
        else {
            productsInStore.add(prod2);
            store.setProducts(productsInStore);
        }
        when(storeService.save(store)).thenReturn(store);


        Store updatedStore = service.saveProd(prod1POJO, store.getId());


        assertThat(updatedStore.getId()).isEqualTo(store.getId());
        assertThat(updatedStore.getProducts()).isEqualTo(productsInStore);
        verifyFindByIdIsCalledOnce(store.getId());
    }

    @Test
    public void whenValidProduct_withInvalidStoreId_thenProductShouldNotBeCreated() {
        Long invalidStoreId = 0L;
        SaveProductInStorePOJO prod1POJO = new SaveProductInStorePOJO("prod1", "origin1", 10, "path", 100 );


        when(storeService.findById(invalidStoreId)).thenThrow(StoreNotFoundException.class);

        assertThrows(StoreNotFoundException.class, () -> service.saveProd(prod1POJO, invalidStoreId));

        verifyFindByIdIsCalledOnce(invalidStoreId);
    }



    private void verifyFindAllIsCalledOnce() {
        verify(repository, times(1)).findAll();
    }

    private void verifyFindByIdIsCalledOnce(Long prodId) {
        verify(storeService, times(1)).findById(prodId);
    }

    private void verifyProdRepFindByIdIsCalledOnce(Long prodId) {
        verify(repository, times(1)).findById(prodId);
    }

}