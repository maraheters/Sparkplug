package com.sparkplug.catalog.service;

import com.sparkplug.catalog.dto.modification.ModificationCreateRequestDto;
import com.sparkplug.catalog.model.*;
import com.sparkplug.catalog.repository.ModificationsRepository;
import com.sparkplug.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModificationsService {

    private final ModificationsRepository repository;
    private final Sort defaultSort;

    @Autowired
    public ModificationsService(ModificationsRepository repository) {
        this.repository = repository;
        this.defaultSort = Sort.by(Sort.Order.asc("name"));
    }

    public List<Modification> getAll() {
        return repository.findAll(defaultSort);
    }

    public Modification getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modification", id));
    }

    public List<Modification> getAllByGenerationId(Long generationId) {
        return repository.findAllByGenerationId(generationId, defaultSort);
    }

    @Transactional
    public Long create(ModificationCreateRequestDto dto) {
        var engine = dto.engine();
        var transmission = dto.transmission();
        var drivetrain = dto.drivetrain();

        var modification = Modification.builder()
                .name(dto.name())
                .generation(Generation.builder().id(dto.generationId()).build())
                .build();

        if (drivetrain != null) {
            modification.setDrivetrain(
                    Drivetrain.builder()
                            .type(drivetrain.type())
                            .modification(modification)
                            .build()
            );
        }

        if (engine != null) {
            modification.setEngine(
                    Engine.builder()
                            .type(engine.type())
                            .fuelType(engine.fuelType())
                            .horsepower(engine.horsepower())
                            .torque(engine.torque())
                            .modification(modification)
                            .build()
            );
        }

        if (transmission != null) {
            modification.setTransmission(
                    Transmission.builder()
                            .type(transmission.type())
                            .numberOfGears(transmission.numberOfGears())
                            .modification(modification)
                            .build()
            );
        }

        return repository.save(modification).getId();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
