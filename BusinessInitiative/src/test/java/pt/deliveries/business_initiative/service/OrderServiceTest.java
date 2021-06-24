package pt.deliveries.business_initiative.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.deliveries.business_initiative.exception.ClientEmailOrPhoneNumberInUseException;
import pt.deliveries.business_initiative.exception.ClientHasNoOrdersWaiting;
import pt.deliveries.business_initiative.exception.OrderNotFoundException;
import pt.deliveries.business_initiative.model.*;
import pt.deliveries.business_initiative.pojo.AddressPOJO;
import pt.deliveries.business_initiative.repository.OrderRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock(lenient = true)
    private OrderRepository repository;

    @Mock(lenient = true)
    private ClientServiceImpl clientService;

    @Mock(lenient = true)
    private ProductServiceImpl productService;

    @Mock(lenient = true)
    private Order_ProductServiceImpl orderProductService;

    @InjectMocks
    private OrderServiceImpl service;

    @BeforeEach
    public void setUp() {
        Address address1 = new Address("city1", "address1", 10, 11);
        Address address2 = new Address("city2", "address2", 10, 11);
        Address address3 = new Address("city3", "address3", 10, 11);
        address1.setId(1L);
        address2.setId(2L);
        address3.setId(3L);

        AddressPOJO address2POJO = new AddressPOJO("city2", "address2", 10, 11);


        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        Client goodClient2 = new Client("cunha2", "cunha@ua.pt", "1234", null, "M", "96000002");
        Client goodClient3 = new Client("cunha3", "cunha@ua.pt", "1234", null, "M", "96000003");
        goodClient1.setId(1L);
        goodClient2.setId(2L);
        goodClient3.setId(3L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));
        goodClient2.setAddresses(new HashSet<>(Collections.singleton(address2)));
        goodClient3.setAddresses(new HashSet<>(Collections.singleton(address3)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        prod1.setId(1L);
        prod2.setId(2L);

        Order_Product orderProduct1 = new Order_Product(null, prod1, 10);
        Order_Product orderProduct2 = new Order_Product(null, prod2, 11);
        orderProduct1.setId(1L);
        orderProduct2.setId(2L);

        Set<Order_Product> orderProductSet1 = new HashSet<>(Arrays.asList(orderProduct1, orderProduct2));
        Set<Order_Product> orderProductSet2 = new HashSet<>(Collections.singletonList(orderProduct2));
        Order order1 = new Order(address1, null, orderProductSet1, goodClient1, "waiting");
        Order order2 = new Order(address2, null, orderProductSet2, goodClient2, "waiting");
        Order order3 = new Order(address3, null, null, goodClient3, "waiting");
        order1.setId(1L);
        order2.setId(2L);
        order3.setId(3L);

        List<Order> allOrders = new ArrayList<>(Arrays.asList(order1, order2, order3));



        Client clientWith1Order = new Client("cunha4", "cunha@ua.pt", "1234", null, "M", "96000004");
        clientWith1Order.setId(4L);
        clientWith1Order.setAddresses(new HashSet<>(Collections.singleton(address1)));
        clientWith1Order.setOrders(new HashSet<>(Collections.singleton(order2)));

        Product newProductForOrder = new Product("prod3", "origin3", 10, "path", 100 );
        newProductForOrder.setId(3L);

        Product alreadyExistingProduct = prod2;
        Long invalidOrderId = 0L;


        when( repository.findAll() ).thenReturn( allOrders );
        when( repository.findById(order1.getId()) ).thenReturn(Optional.of(order1));
        when( repository.findById(invalidOrderId) ).thenThrow( new OrderNotFoundException("Order not found") );

        when( clientService.findById(goodClient3.getId()) ).thenReturn( goodClient3 );
        when( productService.findById(prod1.getId()) ).thenReturn( prod1 );


        when( orderProductService.save( Mockito.any()) ).thenReturn( orderProduct1 );
        when( repository.save( Mockito.any()) ).thenReturn( order3 );


        when( clientService.findById(clientWith1Order.getId()) ).thenReturn( clientWith1Order );
        when( productService.findById(newProductForOrder.getId()) ).thenReturn( newProductForOrder );


        when( productService.findById(alreadyExistingProduct.getId()) ).thenReturn( alreadyExistingProduct );


        when( service.updateOrderAddress(4L, address2POJO)).thenReturn( order2 );
    }

    @Test
    void given3Stores_whenFindAllStores_thenReturnAllStores() {
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
        goodClient3.setAddresses(new HashSet<>(Collections.singleton(address3)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        prod1.setId(1L);
        prod1.setId(2L);

        Order_Product orderProduct1 = new Order_Product(null, prod1, 10);
        Order_Product orderProduct2 = new Order_Product(null, prod2, 11);
        orderProduct1.setId(1L);
        orderProduct2.setId(2L);

        Set<Order_Product> orderProductSet1 = new HashSet<>(Arrays.asList(orderProduct1, orderProduct2));
        Set<Order_Product> orderProductSet2 = new HashSet<>(Collections.singletonList(orderProduct1));
        Order order1 = new Order(address1, null, orderProductSet1, goodClient1, "status1");
        Order order2 = new Order(address2, null, null, goodClient2, "status2");
        Order order3 = new Order(address3, null, orderProductSet2, goodClient3, "status3");
        order1.setId(1L);
        order2.setId(2L);
        order3.setId(3L);

        List<Order> allOrders = service.findAllOrders();

        verifyFindAllIsCalledOnce();
        assertThat(allOrders).hasSize(3).extracting(Order::getId).contains(1L, 2L, 3L);
        assertThat(allOrders).hasSize(3).extracting(Order::getStatus).contains("waiting", "waiting", "waiting");
    }

    @Test
    void whenFindOrderById_thenOrderShouldBeReturned() {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        goodClient1.setId(1L);

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        prod1.setId(1L);
        prod2.setId(2L);

        Order_Product orderProduct1 = new Order_Product(null, prod1, 10);
        Order_Product orderProduct2 = new Order_Product(null, prod2, 11);
        orderProduct1.setId(1L);
        orderProduct2.setId(2L);

        Set<Order_Product> orderProductSet1 = new HashSet<>(Arrays.asList(orderProduct1, orderProduct2));

        Order order1 = new Order(address1, null, orderProductSet1, goodClient1, "waiting");
        order1.setId(1L);

        Order found = service.findOrderById(order1.getId());


        assertThat(found.getId()).isEqualTo(order1.getId());
        assertThat(found.getStatus()).isEqualTo(order1.getStatus());
        assertThat(found.getDeliverAddress().getId()).isEqualTo(order1.getDeliverAddress().getId());
        assertThat(found.getDeliverAddress().getCity()).isEqualTo(order1.getDeliverAddress().getCity());

        verifyFindByIdIsCalledOnce(order1.getId());
    }

    @Test
    void whenFindOrderByInvalidId_thenExceptionShouldBeThrown() {
        Long invalidOrderId = 0L;

        assertThrows(OrderNotFoundException.class, () -> service.findOrderById(invalidOrderId));

        verifyFindByIdIsCalledOnce(invalidOrderId);
    }

    @Test
    @Disabled
    void whenCreateSecondOrder_thenOrderShouldBeSaved() {
        Address address1 = new Address("city3", "address3", 10, 11);
        address1.setId(3L);

        Client goodClient1 = new Client("cunha3", "cunha@ua.pt", "1234", null, "M", "96000003");
        goodClient1.setId(3L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        Order_Product orderProduct = new Order_Product(null, prod1, 10);
        orderProduct.setId(1L);

        Set<Order_Product> orderProductSet = new HashSet<>(Collections.singleton(orderProduct));
        Order order1 = new Order(address1, null, orderProductSet, goodClient1, "waiting");
        order1.setId(3L);

        goodClient1.setOrders(new HashSet<>(Collections.singleton(order1)));

        Order saved = service.updateProductsOrder(4L, null, 3L, 10);


        assertThat(saved.getId()).isEqualTo(order1.getId());
        assertThat(saved.getStatus()).isEqualTo(order1.getStatus());
        assertThat(saved.getDeliverAddress().getId()).isEqualTo(order1.getDeliverAddress().getId());
        assertThat(saved.getDeliverAddress().getCity()).isEqualTo(order1.getDeliverAddress().getCity());

        verifySaveIsCalledOnce(order1);
    }

    @Test
    @Disabled
    void whenIncreaseAmountOfFirstOrder_thenOrderShouldBeUpdated() {
        Address address1 = new Address("city3", "address3", 10, 11);
        address1.setId(3L);

        Client goodClient1 = new Client("cunha3", "cunha@ua.pt", "1234", null, "M", "96000003");
        goodClient1.setId(3L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        Order_Product orderProduct = new Order_Product(null, prod1, 10);
        orderProduct.setId(1L);

        Set<Order_Product> orderProductSet = new HashSet<>(Collections.singleton(orderProduct));
        Order order1 = new Order(address1, null, orderProductSet, goodClient1, "waiting");
        order1.setId(3L);

        goodClient1.setOrders(new HashSet<>(Collections.singleton(order1)));

        Order saved = service.updateProductsOrder(4L, null, 2L, 10);


        assertThat(saved.getId()).isEqualTo(order1.getId());
        assertThat(saved.getStatus()).isEqualTo(order1.getStatus());
        assertThat(saved.getDeliverAddress().getId()).isEqualTo(order1.getDeliverAddress().getId());
        assertThat(saved.getDeliverAddress().getCity()).isEqualTo(order1.getDeliverAddress().getCity());

        verifySaveIsCalledOnce(order1);
    }

    @Test
    void whenUpdateOrderStatus_thenOrderShouldBeUpdated() {
        Address address2 = new Address("city2", "address2", 10, 11);
        address2.setId(2L);

        Client goodClient4 = new Client("cunha4", "cunha@ua.pt", "1234", null, "M", "96000004");
        goodClient4.setId(4L);
        goodClient4.setAddresses(new HashSet<>(Collections.singleton(address2)));

        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        prod2.setId(2L);

        Order_Product orderProduct2 = new Order_Product(null, prod2, 11);
        orderProduct2.setId(2L);

        Set<Order_Product> orderProductSet2 = new HashSet<>(Collections.singletonList(orderProduct2));
        Order order2 = new Order(address2, null, orderProductSet2, goodClient4, "waiting");
        order2.setId(2L);

        goodClient4.setOrders(new HashSet<>(Collections.singleton(order2)));

        String newStatus = "done";
        Order saved = service.updateStatus(4L, newStatus);


        assertThat(saved.getId()).isEqualTo(order2.getId());
        assertThat(saved.getStatus()).isEqualTo(newStatus);
        assertThat(saved.getDeliverAddress().getCompleteAddress()).isEqualTo(order2.getDeliverAddress().getCompleteAddress());
        assertThat(saved.getDeliverAddress().getCity()).isEqualTo(order2.getDeliverAddress().getCity());

        verifySaveIsCalledOnce(order2);
    }

    @Test
    void whenUpdateInvalidOrderStatus_thenOrderShouldNotBeUpdated() {
        Address address1 = new Address("city3", "address3", 10, 11);
        address1.setId(3L);
        Client goodClient1 = new Client("cunha3", "cunha@ua.pt", "1234", null, "M", "96000003");
        goodClient1.setId(3L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));

        try {
            Order saved = service.updateStatus(3L, "done");
            fail( "My method didn't throw when I expected it to" );
        } catch (ClientHasNoOrdersWaiting expectedException) {
            assertThat(expectedException.getMessage()).isEqualTo("Client doest have any orders waiting");
        }
    }


    @Test
    void whenUpdateOrderAddress_thenOrderShouldBeUpdated() {
        AddressPOJO address2POJO = new AddressPOJO("city2", "address2", 10, 11);

        Address address2 = new Address("city2", "address2", 10, 11);
        address2.setId(2L);

        Client goodClient4 = new Client("cunha4", "cunha@ua.pt", "1234", null, "M", "96000004");
        goodClient4.setId(4L);
        goodClient4.setAddresses(new HashSet<>(Collections.singleton(address2)));

        Product prod2 = new Product("prod2", "origin2", 10, "path", 100 );
        prod2.setId(2L);

        Order_Product orderProduct2 = new Order_Product(null, prod2, 11);
        orderProduct2.setId(2L);

        Set<Order_Product> orderProductSet2 = new HashSet<>(Collections.singletonList(orderProduct2));
        Order order2 = new Order(address2, null, orderProductSet2, goodClient4, "waiting");
        order2.setId(2L);

        goodClient4.setOrders(new HashSet<>(Collections.singleton(order2)));

        Order saved = service.updateOrderAddress(4L, address2POJO);


        assertThat(saved.getId()).isEqualTo(order2.getId());
        assertThat(saved.getDeliverAddress().getCompleteAddress()).isEqualTo(order2.getDeliverAddress().getCompleteAddress());
        assertThat(saved.getDeliverAddress().getCity()).isEqualTo(order2.getDeliverAddress().getCity());

        verifySaveIsCalledOnce(order2);
    }

    private void verifyFindAllIsCalledOnce() {
        verify(repository, times(1)).findAll();
    }

    private void verifySaveIsCalledOnce(Order order) {
        verify(repository, times(1)).save(Mockito.any());
    }

    private void verifySaveIsCalledTwice(Order order) {
        verify(repository, times(2)).save(Mockito.any());
    }

    private void verifyFindByIdIsCalledOnce(Long orderId) {
        verify(repository, times(1)).findById(orderId);
    }

}

