package com.sparkplug.catalog.service;

import com.sparkplug.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(GenerationsService.class)
public class GenerationsServiceTests extends ServiceTest {

    @Autowired
    GenerationsService service;

    Long carModelId;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @BeforeEach
    void insertCarModelBeforeEach() {
        var manufacturerId = insertManufacturer();
        carModelId = insertCarModel(manufacturerId);
    }

    @Test
    void create_shouldReturnId() {
        var id = service.create(carModelId, "TestGen", 2000);

        assertNotNull(id);
    }

    @Test
    void getAll_whenNotEmpty_thenResultNotEmpty() {
        insertGeneration(carModelId);

        var result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_whenExists_shouldNotThrow() {
        var id = insertGeneration(carModelId);

        var result = service.getById(id);
    }

    @Test
    void getAllByCarModelId_whenNotEmpty_thenResultNotEmpty() {
        insertGeneration(carModelId);

        var result = service.getAllByCarModelId(carModelId);

        assertEquals(1, result.size());
    }

    @Test
    void getById_whenDoesNotExist_shouldThrow() {
        var id = 8L;

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void delete_whenDeleted_thenResultEmpty() {
        var id = insertGeneration(carModelId);

        service.delete(id);

        var result = service.getAll();

        assertEquals(0, result.size());
    }
}
