package com.sparkplug.catalog.service;

import com.sparkplug.catalog.model.CarModel;
import com.sparkplug.catalog.model.Generation;
import com.sparkplug.catalog.repository.GenerationsRepository;
import com.sparkplug.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GenerationsService {

    private final GenerationsRepository repository;

    @Autowired
    public GenerationsService(GenerationsRepository repository) {
        this.repository = repository;
    }

    public Long create(Long carModelId, String name, Integer startYear) {
        var generation = Generation.builder()
                .carModel(
                        CarModel.builder()
                                .id(carModelId)
                                .build()
                )
                .name(name)
                .startYear(startYear)
                .build();

        return repository.save(generation).getId();
    }

    public List<Generation> getAll() {
        return repository.findAll();
    }

    public Generation getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Generation", id));
    }


    public List<Generation> getAllByCarModelId(Long carModelId) {
        return repository.getAllByCarModelId(carModelId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
