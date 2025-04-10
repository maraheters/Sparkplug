package com.sparkplug.catalog.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "modification", schema = "catalog")
public class Modification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    @ManyToOne
    private Generation generation;

    @OneToOne(mappedBy = "modification", cascade = {MERGE, PERSIST, REMOVE})
    private Drivetrain drivetrain;

    @OneToOne(mappedBy = "modification", cascade = {MERGE, PERSIST, REMOVE})
    private Engine engine;

    @OneToOne(mappedBy = "modification", cascade = {MERGE, PERSIST, REMOVE})
    private Transmission transmission;
}
