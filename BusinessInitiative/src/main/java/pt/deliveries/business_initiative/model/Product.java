package pt.deliveries.business_initiative.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private double price;

    @Column
    private String imgPath;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "store_id", referencedColumnName = "id", nullable = false)
    //private Store store;

    //@ManyToOne
    //@JoinColumn(name="unit_id")
    //private Unit unit;

    //@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    //private Set<O> bookPublishers = new HashSet<>();

    @Column(nullable = false)
    private double weight;

    public Product() { }


    public Product(String name, String origin, double price, String imgPath, double weight) {
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.imgPath = imgPath;
        this.weight = weight;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                ", price=" + price +
                ", imgPath='" + imgPath + '\'' +
                ", weight=" + weight +
                '}';
    }
}
