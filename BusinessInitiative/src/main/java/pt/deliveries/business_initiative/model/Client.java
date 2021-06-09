package pt.deliveries.business_initiative.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
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

    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    //@JoinColumn(name = "client_id")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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