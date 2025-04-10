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
@Table(name = "generation", schema = "catalog")
public class Generation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    private Integer startYear;

    @ManyToOne
    private CarModel carModel;

    @OneToMany(mappedBy = "generation")
    private List<Modification> modifications;

}
