package pt.deliveries.business_iniciative.service;

import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StoreService {

    List<Store> findAllStores();

    Set<Product> findAllProductsInStore(Long storeId);

    List<Store> findByName(String name);

    List<Store> findByAddress(String address);

    Store findByLatAndLng(double lat, double lng);

    Store save(Store store);
}
