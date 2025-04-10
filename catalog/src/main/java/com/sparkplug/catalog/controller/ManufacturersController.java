package com.sparkplug.catalog.controller;

import com.sparkplug.catalog.dto.manufacturer.ManufacturerCreateRequestDto;
import com.sparkplug.catalog.dto.manufacturer.ManufacturerResponseDto;
import com.sparkplug.catalog.mapper.ManufacturerMapper;
import com.sparkplug.catalog.service.ManufacturersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.sparkplug.catalog.controller.ManufacturersController.MANUFACTURERS_ENDPOINT;

@RestController
@RequestMapping(MANUFACTURERS_ENDPOINT)
public class ManufacturersController {

    public static final String MANUFACTURERS_ENDPOINT = "/catalog/manufacturers";

    private final ManufacturersService service;
    private final ManufacturerMapper mapper;

    @Autowired
    public ManufacturersController(ManufacturersService service, ManufacturerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerResponseDto>> getAll() {

        return ResponseEntity.ok(
                service.getAll().stream()
                        .map(mapper::toResponseDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                mapper.toResponseDto(service.getById(id))
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_BASIC', 'ADMIN_MANAGER', 'ADMIN_GOD')")
    public ResponseEntity<String> create(@RequestBody ManufacturerCreateRequestDto dto) {
        var id = service.create(dto.name(), dto.country());
        var uri = URI.create(MANUFACTURERS_ENDPOINT + "/" + id.toString());

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
