package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.CarModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.lang.NonNull;

@Repository
public interface CarModelsRepository extends JpaRepository<CarModel, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"manufacturer", "generations"})
    List<CarModel> findAll(@NonNull Sort sort);

    @EntityGraph(attributePaths = {"manufacturer", "generations"})
    List<CarModel> findAllByManufacturerId(Long id, Sort sort);
}
