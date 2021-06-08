package pt.deliveries.business_iniciative.service;

import pt.deliveries.business_iniciative.model.Product;
import pt.deliveries.business_iniciative.model.Store;

import java.util.List;
import java.util.Set;

public interface StoreService {

    List<Store> findAllStores();

    Set<Product> findAllProductsInStore(Long storeId);

    Store findById(Long id);

    List<Store> findByName(String name);

    List<Store> findByCity(String city);

    List<Store> findByAddress(String address);

    Store findByLatAndLng(double lat, double lng);

    Store saveStore(Store store);
}
