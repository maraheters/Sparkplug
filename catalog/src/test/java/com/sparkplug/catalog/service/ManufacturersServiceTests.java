package com.sparkplug.catalog.service;

import com.sparkplug.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(ManufacturersService.class)
public class ManufacturersServiceTests extends ServiceTest {

    @Autowired
    ManufacturersService service;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void create_shouldReturnId() {
        var id = service.create("name", "country");

        assertNotNull(id);
    }

    @Test
    void getAll_whenNotEmpty_thenResultNotEmpty() {
        insertManufacturer();

        var result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_whenExists_shouldNotThrow() {
        var id = insertManufacturer();

        var result = service.getById(id);
    }

    @Test
    void getById_whenDoesNotExist_shouldThrow() {
        var id = 8L;

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void delete_whenDeleted_thenResultEmpty() {
        var id = insertManufacturer();

        service.delete(id);

        var result = service.getAll();

        assertEquals(0, result.size());
    }
}
