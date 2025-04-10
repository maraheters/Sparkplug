package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationsRepository extends JpaRepository<Generation, Long> {
}
