package pt.deliveries.business_iniciative.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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


    @BeforeEach
    public void setUp() {
        Product prod1 = new Product(1L, "prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product(2L, "prod2", "origin2", 10, "path", 100 );
        Product prod3 = new Product(3L, "prod3", "origin3", 10, "path", 100 );

        List<Product> allProducts = Arrays.asList(prod1, prod2, prod3);

        when(repository.findAll()).thenReturn(allProducts);
        when(repository.findById(prod1.getId())).thenReturn(Optional.of(prod1));
        when(repository.findById(0L)).thenReturn(null);
    }



    @Test
    public void given3Products_whenFindAllProducts_thenReturnAllProducts() {
        Product prod1 = new Product(1L, "prod1", "origin1", 10, "path", 100);
        Product prod2 = new Product(2L, "prod2", "origin2", 10, "path", 100);
        Product prod3 = new Product(3L, "prod3", "origin3", 10, "path", 100);

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



    private void verifyFindAllIsCalledOnce() {
        verify(repository, times(1)).findAll();
    }

    private void verifyFindByIdIsCalledOnce(Long prodId) {
        verify(repository, times(1)).findById(prodId);
    }


}