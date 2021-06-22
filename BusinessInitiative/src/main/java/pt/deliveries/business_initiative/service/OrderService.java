package pt.deliveries.business_initiative.service;

import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrders();

    Order findOrderById(Long orderId);

    Order updateProductsOrder(Long clientId, Long productId, Integer amount);

    Order updateStatus(Long clientId, String status);

    Order updateOrderAddress(Long clientId, Address addressId);
}
