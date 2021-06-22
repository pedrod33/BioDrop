package pt.deliveries.business_initiative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.deliveries.business_initiative.exception.ClientHasNoOrdersWaiting;
import pt.deliveries.business_initiative.exception.ClientNotFoundException;
import pt.deliveries.business_initiative.exception.OrderNotFoundException;
import pt.deliveries.business_initiative.model.*;
import pt.deliveries.business_initiative.pojo.AddressPOJO;
import pt.deliveries.business_initiative.repository.OrderRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository repository;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private Order_ProductServiceImpl orderProductService;

    @Autowired
    private AddressServiceImpl addressService;


    private static final Logger logger
            = Logger.getLogger(
            OrderServiceImpl.class.getName());

    private static final String WAITING = "waiting";
    private static final String WAITING_FOR_RIDER = "waiting_for_rider";


    @Override
    public List<Order> findAllOrders() {
        return repository.findAll();
    }

    @Override
    public Order findOrderById(Long orderId) {
        logger.log(Level.INFO, "Finding order by id ...");

        return repository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public Order updateProductsOrder(Long clientId, Long productId, Integer amount) {

        Client client = clientService.findById(clientId);
        Product prod = productService.findById(productId);

        logger.log(Level.INFO, "Looking for client orders ...");
        Order clientCurrentOrder = null;


        // Verify if the client already has orders created, if he does have, find those in waiting status
        for (Order order : client.getOrders()) {
            if (order.getStatus().equals(WAITING)) {
                logger.log(Level.INFO, "Order waiting found");
                clientCurrentOrder = order;
            }
        }


        // In case the client doesn't have any orders yet
        if ( clientCurrentOrder == null ) {
            logger.log(Level.INFO, "Creating new order for client ...");

            clientCurrentOrder = new Order(null, null, client, WAITING);


            Order_Product orderProduct = new Order_Product(clientCurrentOrder, prod, amount);
            Set<Order_Product> orderProducts = new HashSet<>(Collections.singletonList(orderProduct));
            clientCurrentOrder.setOrderProducts(orderProducts);

            repository.save(clientCurrentOrder);
            orderProductService.save(orderProduct);

            Set<Order> clientsOrders = new HashSet<>(Collections.singletonList(clientCurrentOrder));
            client.setOrders(clientsOrders);
            clientService.save(client);
            logger.log(Level.INFO, "Order created and saved");

        } else {
            logger.log(Level.INFO, "Updating order ...");

            // Se o produto ja estiver listado na order, aumentar o amount
            Order_Product orderProduct = null;
            logger.log(Level.INFO, "Looking if product already exists in the order ...");
            for( Order_Product orderProductListed : clientCurrentOrder.getOrderProducts()) {
                if( orderProductListed.getProduct().getId().equals( prod.getId() ) ) {
                    orderProductListed.setAmount( orderProductListed.getAmount() + amount );
                    orderProduct = orderProductListed;
                    logger.log(Level.INFO, "Found product {0}! Amount increased", prod.getId());
                }
            }

            if (orderProduct == null) {
                logger.log(Level.INFO, "Not Found! Creating new Order_Product");
                orderProduct = new Order_Product(clientCurrentOrder, prod, amount);
            }

            clientCurrentOrder.getOrderProducts().add(orderProduct);
            orderProductService.save(orderProduct);
            logger.log(Level.INFO, "Order updated and saved");
        }

        return repository.save(clientCurrentOrder);
    }

    @Override
    public Order updateStatus(Long clientId, String orderStatus) {
        Client client = clientService.findById(clientId);

        logger.log(Level.INFO, "Looking for client orders ...");
        Order clientCurrentOrder = null;
        logger.log(Level.INFO, client.getOrders().toString());


        // Verificar se o client tem orders criadas e se tiver encontrar a que esta no estado waiting
        for (Order order : client.getOrders()) {
            logger.log(Level.INFO, order.toString());

            if (order.getStatus().equals(WAITING) || order.getStatus().equals(WAITING_FOR_RIDER)) {
                order.setStatus(orderStatus);
                clientCurrentOrder = order;
                logger.log(Level.INFO, "Order waiting found and updated");
            }
        }


        // In case the client doesn't have any orders yet
        if ( clientCurrentOrder == null ) {
            logger.log(Level.INFO, "Client doest have any orders waiting, cant update  ...");
            throw new ClientHasNoOrdersWaiting("Client doest have any orders waiting");
        } else {
            logger.log(Level.INFO, "Updating order status ...");
            clientCurrentOrder.setStatus(orderStatus);
        }

        repository.save(clientCurrentOrder);
        logger.log(Level.INFO, "Order status updated and saved");
        return clientCurrentOrder;
    }

    @Override
    public Order updateOrderAddress(Long clientId, AddressPOJO addressPOJO) {

        Address address = new Address();
        address.setCity(addressPOJO.getCity());
        address.setCompleteAddress(addressPOJO.getCompleteAddress());
        address.setLatitude(addressPOJO.getLatitude());
        address.setLongitude(addressPOJO.getLongitude());

        Client client = clientService.findById(clientId);

        logger.log(Level.INFO, "Looking for client orders ...");
        Order clientCurrentOrder = null;
        logger.log(Level.INFO, client.getOrders().toString());


        // Verificar se o client tem orders criadas e se tiver encontrar a que esta no estado waiting
        for (Order order : client.getOrders()) {
            logger.log(Level.INFO, order.toString());

            if (order.getStatus().equals(WAITING)) {
                clientCurrentOrder = order;
                logger.log(Level.INFO, "Order waiting found");
            }
        }


        // In case the client doesn't have any orders yet
        if ( clientCurrentOrder == null ) {
            logger.log(Level.INFO, "Client doest have any orders waiting, cant update  ...");
            throw new ClientHasNoOrdersWaiting("Client doest have any orders waiting");
        } else {
            logger.log(Level.INFO, "Updating order address ...");
            clientCurrentOrder.setAddress(address);
        }

        repository.save(clientCurrentOrder);
        logger.log(Level.INFO, "Order address updated and saved");
        return clientCurrentOrder;
    }

}
