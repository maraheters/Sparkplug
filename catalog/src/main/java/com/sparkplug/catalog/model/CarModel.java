package com.sparkplug.catalog.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_model", schema = "catalog")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    @ManyToOne
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "carModel")
    private List<Generation> generations;
}
