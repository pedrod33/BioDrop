package pt.deliveries.business_initiative.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String completeAddress;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    //@OneToOne(mappedBy = "address")
    //private Store store;

    //@ManyToOne(cascade=CascadeType.ALL)
    //@JoinColumn(name="client_id")
    //private Client client;


    public Address() { }

    public Address(String city, String completeAddress, double latitude, double longitude) {
        this.city = city;
        this.completeAddress = completeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Double.compare(address1.latitude, latitude) == 0 &&
                Double.compare(address1.longitude, longitude) == 0 &&
                Objects.equals(id, address1.id) &&
                Objects.equals(city, address1.city) &&
                Objects.equals(completeAddress, address1.completeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, completeAddress, latitude, longitude);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", completeAddress='" + completeAddress + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
