package pt.deliveries.business_initiative.service;

import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Client;

import java.util.List;

public interface AddressService {

    List<Address> findAllAddresses();

    Client saveClientAddress(Address address, Long clientId);

}
