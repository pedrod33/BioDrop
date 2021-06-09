package pt.deliveries.business_initiative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.deliveries.business_initiative.model.Order_Product;
import pt.deliveries.business_initiative.repository.Order_ProductRepository;

@Service
public class Order_ProductServiceImpl implements Order_ProductService{

    @Autowired
    Order_ProductRepository repository;


    @Override
    public Order_Product save(Order_Product orderProduct) {
        return repository.save(orderProduct);
    }
}
