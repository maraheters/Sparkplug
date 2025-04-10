package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturersRepository extends JpaRepository<Manufacturer, Long> {
}
