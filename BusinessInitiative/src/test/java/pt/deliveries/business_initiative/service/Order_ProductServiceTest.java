package pt.deliveries.business_initiative.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_initiative.model.*;
import pt.deliveries.business_initiative.repository.Order_ProductRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Order_ProductServiceTest {

    @Mock(lenient = true)
    private Order_ProductRepository repository;

    @InjectMocks
    private Order_ProductServiceImpl service;

    @BeforeEach
    public void setUp() {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        goodClient1.setId(1L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));


        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        Order_Product orderProduct1 = new Order_Product(null, prod1, 10);
        orderProduct1.setId(1L);

        Set<Order_Product> orderProductSet1 = new HashSet<>(Arrays.asList(orderProduct1, orderProduct1));
        Order order1 = new Order(address1, orderProductSet1, goodClient1, "waiting");
        order1.setId(1L);


        when( repository.save(Mockito.any()) ).thenReturn( orderProduct1 );
    }


    @Test
    void whenCreateFirstOrder_thenOrderShouldBeSaved() {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        goodClient1.setId(1L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        Order_Product orderProduct = new Order_Product(null, prod1, 10);
        orderProduct.setId(1L);

        Set<Order_Product> orderProductSet = new HashSet<>(Arrays.asList(orderProduct, orderProduct));
        Order order1 = new Order(address1, orderProductSet, goodClient1, "waiting");
        order1.setId(3L);


        Order_Product saved = service.save(orderProduct);

        assertThat(saved.getId()).isEqualTo(orderProduct.getId());
        assertThat(saved.getProduct().getName()).isEqualTo(orderProduct.getProduct().getName());
        assertThat(saved.getAmount()).isEqualTo(orderProduct.getAmount());

        verifySaveIsCalledOnce(orderProduct);
    }


    private void verifySaveIsCalledOnce(Order_Product orderProduct) {
        verify(repository, times(1)).save(Mockito.any());
    }

}
