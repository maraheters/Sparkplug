package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Modification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModificationsRepository extends JpaRepository<Modification, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"generation"})
    List<Modification> findAll(@NonNull Sort sort);

    @EntityGraph(attributePaths = {"generation"})
    List<Modification> findAllByGenerationId(Long id, Sort sort);
}
