package pt.deliveries.business_iniciative.service;

import pt.deliveries.business_iniciative.model.Client;
import pt.deliveries.business_iniciative.model.Store;

import java.util.List;

public interface StoreService {

    List<Store> findAllStores();

    Store findByName(String name);

    Store findByAddress(String address);

    Store findByLatAndLng(double lat, double lng);

    Store save(Store store);
}
