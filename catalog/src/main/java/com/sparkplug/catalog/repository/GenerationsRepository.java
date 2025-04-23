package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Generation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationsRepository extends JpaRepository<Generation, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"carModel", "modifications"})
    List<Generation> findAll(@NonNull Sort sort);

    @EntityGraph(attributePaths = {"carModel", "modifications"})
    List<Generation> getAllByCarModelId(Long carModelId, Sort sort);
}
