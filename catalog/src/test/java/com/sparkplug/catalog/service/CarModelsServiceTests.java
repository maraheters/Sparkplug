package com.sparkplug.catalog.service;

import com.sparkplug.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(CarModelsService.class)
public class CarModelsServiceTests extends ServiceTest {

    @Autowired
    CarModelsService service;

    Long manufacturerId;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @BeforeEach
    void insertManufacturerBeforeEach() {
        manufacturerId = insertManufacturer();
    }

    @Test
    void create_shouldReturnId() {
        var id = service.create("name", manufacturerId);

        assertNotNull(id);
    }

    @Test
    void getAll_whenNotEmpty_thenResultNotEmpty() {
        insertCarModel(manufacturerId);

        var result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_whenExists_shouldNotThrow() {
        var id = insertCarModel(manufacturerId);

        var result = service.getById(id);
    }

    @Test
    void getAllByManufacturerId_whenNotEmpty_thenResultNotEmpty() {
        insertCarModel(manufacturerId);

        var result = service.getAllByManufacturerId(manufacturerId);

        assertEquals(1, result.size());
    }

    @Test
    void getById_whenDoesNotExist_shouldThrow() {
        var id = 8L;

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void delete_whenDeleted_thenResultEmpty() {
        var id = insertCarModel(manufacturerId);

        service.delete(id);

        var result = service.getAll();

        assertEquals(0, result.size());
    }
}
