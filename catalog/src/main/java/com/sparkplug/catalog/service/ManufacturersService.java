package com.sparkplug.catalog.service;

import com.sparkplug.catalog.model.Manufacturer;
import com.sparkplug.catalog.repository.ManufacturersRepository;
import com.sparkplug.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturersService {

    private final ManufacturersRepository repository;

    @Autowired
    public ManufacturersService(ManufacturersRepository repository) {
        this.repository = repository;
    }

    public Long create(String name, String country) {
        var manufacturer = Manufacturer.builder()
                .name(name)
                .country(country)
                .build();

        return repository.save(manufacturer).getId();
    }

    public List<Manufacturer> getAll() {
        return repository.findAll();
    }

    public Manufacturer getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
