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
@Table(name = "drivetrain", schema = "catalog")
public class Drivetrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String type; // e.g., "AWD", "FWD"

    @OneToOne
    @JoinColumn(name = "modification_id", nullable = false)
    private Modification modification;
}
