package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationsRepository extends JpaRepository<Generation, Long> {

    List<Generation> getAllByCarModelId(Long carModelId);
}
