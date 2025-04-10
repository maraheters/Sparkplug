package com.sparkplug.catalog.dto.carModel;

import java.util.List;

public record CarModelResponseDto(
        Long id, String name, ManufacturerDto manufacturer, List<GenerationDto> generations) {

    /**
     * DTO for {@link com.sparkplug.catalog.model.Manufacturer}
     */
    public record ManufacturerDto(Long id, String name) {
    }

    /**
     * DTO for {@link com.sparkplug.catalog.model.Generation}
    */
    public record GenerationDto(Long id, String name) {
    }
}
