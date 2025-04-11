package com.sparkplug.catalog.controller.generations;

import com.sparkplug.catalog.dto.generation.GenerationResponseDto;
import com.sparkplug.catalog.mapper.GenerationMapper;
import com.sparkplug.catalog.service.GenerationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sparkplug.catalog.controller.generations.CarModelGenerationsController.CAR_MODEL_GENERATIONS_ENDPOINT;

@RestController
@RequestMapping(CAR_MODEL_GENERATIONS_ENDPOINT)
public class CarModelGenerationsController {

    public static final String CAR_MODEL_GENERATIONS_ENDPOINT = "/catalog/models/{id}/generations";

    private final GenerationsService service;
    private final GenerationMapper mapper;

    @Autowired
    public CarModelGenerationsController(GenerationsService service, GenerationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<GenerationResponseDto>> getAll(@PathVariable("id") Long carModelId) {
        return ResponseEntity.ok(
              service.getAllByCarModelId(carModelId).stream()
                      .map(mapper::toResponseDto)
                      .toList()
        );
    }
}
