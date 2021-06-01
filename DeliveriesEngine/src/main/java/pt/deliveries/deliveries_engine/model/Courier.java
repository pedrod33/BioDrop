package pt.deliveries.deliveries_engine.model;

import javax.persistence.*;

@Entity
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
}
