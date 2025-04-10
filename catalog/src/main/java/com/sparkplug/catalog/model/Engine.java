package com.sparkplug.catalog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "engine", schema = "catalog")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String fuelType;
    private String type;        // e.g. V6
    private Integer horsepower;
    private Integer torque;

    @OneToOne
    @JoinColumn(name = "modification_id", nullable = false)
    private Modification modification;
}
