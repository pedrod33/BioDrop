package pt.deliveries.business_initiative.controller;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.deliveries.business_initiative.JsonUtil;
import pt.deliveries.business_initiative.exception.ClientEmailOrPasswordIncorrectException;
import pt.deliveries.business_initiative.exception.OrderNotFoundException;
import pt.deliveries.business_initiative.model.*;
import pt.deliveries.business_initiative.service.OrderServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderRestController.class)
class OrderRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderServiceImpl service;


    @Test
    void given3Orders_whenFindAllOrders_thenReturnAllOrders() throws Exception {
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
        Order order1 = new Order(address1, orderProductSet1, goodClient1, "status1");
        Order order2 = new Order(address2, null, goodClient2, "status2");
        Order order3 = new Order(address3, orderProductSet2, goodClient3, "status3");
        order1.setId(1L);
        order2.setId(2L);
        order3.setId(3L);


        List<Order> allOrders = new ArrayList<>(Arrays.asList(order1, order2, order3));

        when( service.findAllOrders() ).thenReturn( allOrders );

        mvc.perform(get("/businesses-api/orders/allOrders").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].id", is(1)))
            .andExpect(jsonPath("$.[0].address.city", is("city1")))
            .andExpect(jsonPath("$.[0].orderProducts.[0].product.name", anyOf(is("prod1"), is("prod2"))))
            .andExpect(jsonPath("$.[0].orderProducts.[0].amount", anyOf(is(10), is(11))))
            .andExpect(jsonPath("$.[0].orderProducts.[1].product.name", anyOf(is("prod1"), is("prod2"))))
            .andExpect(jsonPath("$.[0].orderProducts.[1].amount", anyOf(is(10), is(11))))
            .andExpect(jsonPath("$.[0].status", is("status1")))
            .andExpect(jsonPath("$.[1].id", is(2)))
            .andExpect(jsonPath("$.[1].address.city", is("city2")))
            .andExpect(jsonPath("$.[1].orderProducts").value(IsNull.nullValue()))
            .andExpect(jsonPath("$.[1].status", is("status2")))
            .andExpect(jsonPath("$.[2].id", is(3)))
            .andExpect(jsonPath("$.[2].address.city", is("city3")))
            .andExpect(jsonPath("$.[2].orderProducts.[0].id", is(1)))
            .andExpect(jsonPath("$.[2].orderProducts.[0].product", is(2)))
            .andExpect(jsonPath("$.[2].orderProducts.[0].amount", is(10)))
            .andExpect(jsonPath("$.[2].status", is("status3")));

        verify(service, times(1)).findAllOrders();

        assertThat(allOrders).hasSize(3).extracting(Order::getId).contains(1L, 2L, 3L);
        assertThat(allOrders).hasSize(3).extracting(Order::getStatus).contains("status1", "status2", "status3");
    }

    @Test
    void whenFindOrderByValidId_thenReturnOrder() throws Exception {

        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        goodClient1.setId(1L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        Order_Product orderProduct1 = new Order_Product(null, prod1, 10);
        orderProduct1.setId(1L);

        Set<Order_Product> orderProductSet = new HashSet<>(Collections.singleton(orderProduct1));
        Order order1 = new Order(address1, orderProductSet, goodClient1, "waiting");
        order1.setId(1L);


        when( service.findOrderById(order1.getId()) ).thenReturn( order1 );

        mvc.perform(get("/businesses-api/orders/?orderId="+order1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.address.city", is("city1")))
                .andExpect(jsonPath("$.orderProducts.[0].product.name", is("prod1")))
                .andExpect(jsonPath("$.orderProducts.[0].amount", is(10)))
                .andExpect(jsonPath("$.status", is("waiting")));

        verify(service, times(1)).findOrderById(order1.getId());
    }

    @Test
    void whenFindOrderByInvalidId_thenReturnOrder() throws Exception {
        when( service.findOrderById(0L) ).thenThrow(OrderNotFoundException.class);

        mvc.perform(get("/businesses-api/orders/?orderId=0").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotFoundException));

        verify(service, times(1)).findOrderById(0L);
    }

    @Test
    void whenCreateValidOrder_thenReturnOrder() throws Exception {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);


        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        goodClient1.setId(1L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);


        Order_Product orderProduct1 = new Order_Product(null, prod1, 10);
        orderProduct1.setId(1L);

        Set<Order_Product> orderProductSet1 = new HashSet<>(Collections.singleton(orderProduct1));
        Order order1 = new Order(address1, orderProductSet1, goodClient1, "status1");
        order1.setId(1L);


        when( service.updateProductsOrder(goodClient1.getId(), prod1.getId(), 1 ) ).thenReturn( order1 );


        mvc.perform(put("/businesses-api/orders/updateProductsOrder?clientId=1&productId=1&amount=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("address.city", is("city1")))
                .andExpect(jsonPath("address.completeAddress", is("address1")))
                .andExpect(jsonPath("address.latitude", is(10.0)))
                .andExpect(jsonPath("address.longitude", is(11.0)))
                .andExpect(jsonPath("orderProducts.[0].product.name", is("prod1")))
                .andExpect(jsonPath("orderProducts.[0].product.origin", is("origin1")))
                .andExpect(jsonPath("orderProducts.[0].product.price", is(10.0)))
                .andExpect(jsonPath("orderProducts.[0].product.imgPath", is("path")))
                .andExpect(jsonPath("orderProducts.[0].product.weight", is(100.0)))
                .andExpect(jsonPath("orderProducts.[0].amount", is(10)))
                .andExpect(jsonPath("status", is("status1")));

        verify(service, times(1)).updateProductsOrder( goodClient1.getId(), prod1.getId(), 1 );
    }

    @Test
    void whenUpdatingOrderStatus_thenReturnUpdatedOrder() throws Exception {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        goodClient1.setId(1L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        Order_Product orderProduct1 = new Order_Product(null, prod1, 10);
        orderProduct1.setId(1L);

        Set<Order_Product> orderProductSet1 = new HashSet<>(Collections.singleton(orderProduct1));
        Order order1 = new Order(address1, orderProductSet1, goodClient1, "done");
        order1.setId(1L);

        Set<Order> clientOrders = new HashSet<>(Collections.singleton(order1));
        goodClient1.setOrders(clientOrders);


        when( service.updateStatus(goodClient1.getId(),"done" ) ).thenReturn( order1 );


        mvc.perform(put("/businesses-api/orders/updateStatus?clientId=1&orderStatus=done").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("address.city", is("city1")))
                .andExpect(jsonPath("address.completeAddress", is("address1")))
                .andExpect(jsonPath("address.latitude", is(10.0)))
                .andExpect(jsonPath("address.longitude", is(11.0)))
                .andExpect(jsonPath("orderProducts.[0].product.name", is("prod1")))
                .andExpect(jsonPath("orderProducts.[0].product.origin", is("origin1")))
                .andExpect(jsonPath("orderProducts.[0].product.price", is(10.0)))
                .andExpect(jsonPath("orderProducts.[0].product.imgPath", is("path")))
                .andExpect(jsonPath("orderProducts.[0].product.weight", is(100.0)))
                .andExpect(jsonPath("orderProducts.[0].amount", is(10)))
                .andExpect(jsonPath("status", is("done")));

        verify(service, times(1)).updateStatus(goodClient1.getId(),"done" );
    }

    @Test
    void whenUpdatingOrderAddress_thenReturnUpdatedOrder() throws Exception {
        Address address1 = new Address("city1", "address1", 10, 11);
        address1.setId(1L);

        Client goodClient1 = new Client("cunha1", "cunha@ua.pt", "1234", null, "M", "96000001");
        goodClient1.setId(1L);
        goodClient1.setAddresses(new HashSet<>(Collections.singleton(address1)));

        Product prod1 = new Product("prod1", "origin1", 10, "path", 100 );
        prod1.setId(1L);

        Order_Product orderProduct1 = new Order_Product(null, prod1, 10);
        orderProduct1.setId(1L);

        Set<Order_Product> orderProductSet1 = new HashSet<>(Collections.singleton(orderProduct1));
        Order order1 = new Order(address1, orderProductSet1, goodClient1, "done");
        order1.setId(1L);

        Set<Order> clientOrders = new HashSet<>(Collections.singleton(order1));
        goodClient1.setOrders(clientOrders);


        when( service.updateOrderAddress(goodClient1.getId(), address1 ) ).thenReturn( order1 );


        mvc.perform(put("/businesses-api/orders/updateOrderAddress?clientId=1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(address1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("address.city", is("city1")))
                .andExpect(jsonPath("address.completeAddress", is("address1")))
                .andExpect(jsonPath("address.latitude", is(10.0)))
                .andExpect(jsonPath("address.longitude", is(11.0)))
                .andExpect(jsonPath("orderProducts.[0].product.name", is("prod1")))
                .andExpect(jsonPath("orderProducts.[0].product.origin", is("origin1")))
                .andExpect(jsonPath("orderProducts.[0].product.price", is(10.0)))
                .andExpect(jsonPath("orderProducts.[0].product.imgPath", is("path")))
                .andExpect(jsonPath("orderProducts.[0].product.weight", is(100.0)))
                .andExpect(jsonPath("orderProducts.[0].amount", is(10)))
                .andExpect(jsonPath("status", is("done")));

        verify(service, times(1)).updateOrderAddress(goodClient1.getId(),address1 );
    }
}

