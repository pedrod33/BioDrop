package pt.deliveries.business_initiative.service;

import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Order;
import pt.deliveries.business_initiative.pojo.AddressPOJO;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrders();

    List<Order> findAllOrdersByStatus(String status);

    Order findOrderById(Long orderId);

    Order updateProductsOrder(Long clientId, Long storeId, Long productId, Integer amount);

    Order updateStatus(Long clientId, String status);

    Order updateStatus2(Long orderId, String status);

    Order updateOrderAddress(Long clientId, AddressPOJO address);
}
