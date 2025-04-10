package com.sparkplug.catalog.mapper;

import com.sparkplug.catalog.dto.manufacturer.ManufacturerResponseDto;
import com.sparkplug.catalog.model.Manufacturer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManufacturerMapper {

    ManufacturerResponseDto toResponseDto(Manufacturer m);
}
