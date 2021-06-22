package pt.deliveries.deliveries_engine.Model;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id", referencedColumnName = "id")
    private Delivery delivery;

    @Column(name="score", nullable = false)
    private int score;

    @Column(name="description")
    private String description;

    public Rating(int score, String description) {
        this.score = score;
        this.description = description;
    }

    public Rating(int score) {
        this.score = score;
    }

    public Rating(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", delivery=" + delivery +
                ", score=" + score +
                ", score=" + score +
                ", description='" + description + '\'' +
                '}';
    }
}
