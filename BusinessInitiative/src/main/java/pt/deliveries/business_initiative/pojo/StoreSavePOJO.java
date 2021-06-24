package pt.deliveries.business_initiative.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.deliveries.business_initiative.model.Address;
import pt.deliveries.business_initiative.model.Product;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreSavePOJO {

    private String name;

    private Address address;

    private Set<Product> products = new HashSet<>();

}
