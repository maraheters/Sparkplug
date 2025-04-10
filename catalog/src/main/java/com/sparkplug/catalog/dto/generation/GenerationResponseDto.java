package com.sparkplug.catalog.dto.generation;

import java.util.List;

public record GenerationResponseDto(
        Long id, String name, Integer startYear, List<Modification> modifications) {

    /**
     * Dto for {@link com.sparkplug.catalog.dto.generation.GenerationResponseDto}
     */
    public record Modification(Long id, String name){}
}
