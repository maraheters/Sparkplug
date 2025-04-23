package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Manufacturer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturersRepository extends JpaRepository<Manufacturer, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"models"})
    List<Manufacturer> findAll(@NonNull Sort sort);
}
