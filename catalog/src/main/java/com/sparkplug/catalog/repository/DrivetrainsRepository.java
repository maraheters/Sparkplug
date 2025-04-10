package com.sparkplug.catalog.repository;

import com.sparkplug.catalog.model.Drivetrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrivetrainsRepository extends JpaRepository<Drivetrain, Long> {
}
