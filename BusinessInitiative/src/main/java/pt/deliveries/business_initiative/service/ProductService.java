package pt.deliveries.business_initiative.service;

import pt.deliveries.business_initiative.model.Product;
import pt.deliveries.business_initiative.model.Store;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    Product findById(Long id);

    Store saveProd(Product product, Long store_id);

}
