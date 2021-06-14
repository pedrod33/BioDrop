package pt.deliveries.business_initiative.service;

import pt.deliveries.business_initiative.model.Product;
import pt.deliveries.business_initiative.model.Store;
import pt.deliveries.business_initiative.pojo.SaveProductInStorePOJO;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    Product findById(Long id);

    Store saveProd(SaveProductInStorePOJO productPOJO, Long store_id);

}
