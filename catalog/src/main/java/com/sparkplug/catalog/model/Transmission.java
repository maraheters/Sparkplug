package com.sparkplug.catalog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transmission", schema = "catalog")
public class Transmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String type; // e.g. automatic, manual
    private Integer numberOfGears;

    @OneToOne
    @JoinColumn(name = "modification_id", nullable = false)
    private Modification modification;
}
