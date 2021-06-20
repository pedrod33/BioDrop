package pt.deliveries.deliveries_engine.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "courier")
public class Courier {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id", nullable = false)
    private Supervisor supervisor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String gender;

    @Column(unique = true, nullable = false)
    private Long phoneNumber;


    @OneToMany(mappedBy = "courier", fetch = FetchType.LAZY)
    private Set<Delivery> deliveries;

    public Courier( String name, String email, String password, String gender, Long phoneNumber, Supervisor supervisor, Vehicle vehicle) {
        this.supervisor = supervisor;
        this.vehicle = vehicle;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Courier() {}

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "supervisor=" + supervisor +
                ", vehicle=" + vehicle +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
