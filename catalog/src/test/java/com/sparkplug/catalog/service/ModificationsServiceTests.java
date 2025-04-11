package com.sparkplug.catalog.service;

import com.sparkplug.catalog.dto.modification.ModificationCreateRequestDto;
import com.sparkplug.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(ModificationsService.class)
public class ModificationsServiceTests extends ServiceTest {

    @Autowired
    ModificationsService service;

    Long generationId;

    ModificationCreateRequestDto dto;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @BeforeEach
    void insertGenerationBeforeEach() {
        var manufacturerId = insertManufacturer();
        var carModelId = insertCarModel(manufacturerId);
        generationId = insertGeneration(carModelId);

        dto = new ModificationCreateRequestDto(
                "TestMod",
                generationId,
                new ModificationCreateRequestDto.DrivetrainCreateRequestDto("Type"),
                new ModificationCreateRequestDto.EngineCreateRequestDto("fuelType", "type", 100, 100),
                new ModificationCreateRequestDto.TransmissionCreateRequestDto("type", 5));
    }

    @Test
    void create_shouldReturnId() {
        var id = service.create(dto);

        assertNotNull(id);
    }

    @Test
    void getAll_whenNotEmpty_thenResultNotEmpty() {
        insertModification(generationId);

        var result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_whenExists_shouldNotThrow() {
        var id = insertModification(generationId);

        var result = service.getById(id);
    }

    @Test
    void getById_whenDoesNotExist_shouldThrow() {
        var id = 8L;

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void getAllByGenerationId_whenNotEmpty_thenResultNotEmpty() {
        insertModification(generationId);

        var result = service.getAllByGenerationId(generationId);

        assertEquals(1, result.size());
    }

    @Test
    void delete_whenDeleted_thenResultEmpty() {
        var id = insertModification(generationId);

        service.delete(id);

        var result = service.getAll();

        assertEquals(0, result.size());
    }
}
