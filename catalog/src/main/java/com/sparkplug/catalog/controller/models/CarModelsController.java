package com.sparkplug.catalog.controller.models;

import com.sparkplug.catalog.dto.carModel.CarModelCreateRequestDto;
import com.sparkplug.catalog.dto.carModel.CarModelResponseDto;
import com.sparkplug.catalog.mapper.CarModelMapper;
import com.sparkplug.catalog.service.CarModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.sparkplug.catalog.controller.models.CarModelsController.MODELS_ENDPOINT;

@RestController
@RequestMapping(MODELS_ENDPOINT)
public class CarModelsController {

    public static final String MODELS_ENDPOINT = "/catalog/models";

    private final CarModelsService service;
    private final CarModelMapper mapper;

    @Autowired
    public CarModelsController(CarModelsService service, CarModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CarModelResponseDto>> getAll() {
        return ResponseEntity.ok(
                service.getAll().stream()
                        .map(mapper::toResponseDto)
                        .toList()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<CarModelResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                mapper.toResponseDto(service.getById(id))
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_BASIC', 'ADMIN_MANAGER', 'ADMIN_GOD')")
    public ResponseEntity<String> create(@RequestBody CarModelCreateRequestDto dto) {
        var id = service.create(dto.name(), dto.manufacturerId());
        var uri = URI.create(MODELS_ENDPOINT + "/" + id.toString());

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
