package pt.deliveries.business_initiative.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy="client")
    private Set<Order> orders = new HashSet<>();

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    public Client() { }

    public Client(String name, String email, String password, Set<Address> addresses, String gender, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.addresses = addresses;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Client(String name, String email, String password, Set<Address> addresses, Set<Order> orders, String gender, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.addresses = addresses;
        this.orders = orders;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", addresses=" + addresses +
                ", orders=" + orders +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}