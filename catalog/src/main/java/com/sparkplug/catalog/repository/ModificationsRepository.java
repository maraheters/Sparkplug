package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Modification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModificationsRepository extends JpaRepository<Modification, Long> {
}
