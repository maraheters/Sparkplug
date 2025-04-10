package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.CarModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelsRepository extends JpaRepository<CarModel, Long> {

    @Query("SELECT cm FROM CarModel cm WHERE cm.manufacturer.id = :id")
    List<CarModel> findAllByManufacturerId(Long manufacturerId, Sort sort);
}
