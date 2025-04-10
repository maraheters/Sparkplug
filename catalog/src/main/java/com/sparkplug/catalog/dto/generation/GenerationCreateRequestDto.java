package com.sparkplug.catalog.dto.generation;

public record GenerationCreateRequestDto(Long carModelId, String name, Integer startYear) {
}
