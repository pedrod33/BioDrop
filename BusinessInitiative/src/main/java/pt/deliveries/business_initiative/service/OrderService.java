package pt.deliveries.business_initiative.service;

import pt.deliveries.business_initiative.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrders();

    Order save(Long clientId, Long productId, Integer amount);

    Order updateStatus(Long clientId, String status);

}
