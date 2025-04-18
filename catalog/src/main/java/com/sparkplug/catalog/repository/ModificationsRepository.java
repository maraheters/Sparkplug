package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Modification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModificationsRepository extends JpaRepository<Modification, Long> {

    List<Modification> findAllByGenerationId(Long id, Sort sort);
}
