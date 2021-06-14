package pt.deliveries.business_initiative.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "store_id")
    private Set<Product> products = new HashSet<>();


    public Store() { }

    public Store(String name, Address address, Set<Product> products) {
        this.name = name;
        this.address = address;
        this.products = products;
    }

}
