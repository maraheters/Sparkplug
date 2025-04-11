package com.sparkplug.catalog.controller.modifications;

import com.sparkplug.catalog.dto.modification.ModificationCreateRequestDto;
import com.sparkplug.catalog.dto.modification.ModificationResponseDto;
import com.sparkplug.catalog.mapper.ModificationMapper;
import com.sparkplug.catalog.service.ModificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.sparkplug.catalog.controller.modifications.ModificationsController.MODIFICATIONS_ENDPOINT;

@RestController
@RequestMapping(MODIFICATIONS_ENDPOINT)
public class ModificationsController {

    public static final String MODIFICATIONS_ENDPOINT = "/catalog/modifications";

    private final ModificationsService service;
    private final ModificationMapper mapper;

    @Autowired
    public ModificationsController(ModificationsService service, ModificationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ModificationResponseDto>> getAll() {

        return ResponseEntity.ok(
                service.getAll().stream()
                        .map(mapper::toResponseDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModificationResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                mapper.toResponseDto(service.getById(id))
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_BASIC', 'ADMIN_MANAGER', 'ADMIN_GOD')")
    public ResponseEntity<String> create(@RequestBody ModificationCreateRequestDto dto) {
        var id = service.create(dto);
        var uri = URI.create(MODIFICATIONS_ENDPOINT + "/" + id.toString());

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
