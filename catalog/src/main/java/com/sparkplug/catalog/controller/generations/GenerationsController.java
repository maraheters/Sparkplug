package com.sparkplug.catalog.controller.generations;

import com.sparkplug.catalog.dto.generation.GenerationCreateRequestDto;
import com.sparkplug.catalog.dto.generation.GenerationResponseDto;
import com.sparkplug.catalog.mapper.GenerationMapper;
import com.sparkplug.catalog.service.GenerationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.sparkplug.catalog.controller.generations.GenerationsController.GENERATIONS_ENDPOINT;

@RestController
@RequestMapping(GENERATIONS_ENDPOINT)
public class GenerationsController {

    public static final String GENERATIONS_ENDPOINT = "/catalog/generations";

    private final GenerationsService service;
    private final GenerationMapper mapper;

    @Autowired
    public GenerationsController(GenerationsService service, GenerationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<GenerationResponseDto>> getAll() {
        return ResponseEntity.ok(
                service.getAll().stream()
                        .map(mapper::toResponseDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenerationResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                mapper.toResponseDto(service.getById(id))
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_BASIC', 'ADMIN_MANAGER', 'ADMIN_GOD')")
    public ResponseEntity<String> create(@RequestBody GenerationCreateRequestDto dto) {
        var id = service.create(dto.carModelId(), dto.name(), dto.startYear());
        var uri = URI.create(GENERATIONS_ENDPOINT + "/" + id.toString());

        return ResponseEntity
                .created(uri)
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_BASIC', 'ADMIN_MANAGER', 'ADMIN_GOD')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }

}
