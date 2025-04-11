package com.sparkplug.catalog.mapper;

import com.sparkplug.catalog.dto.modification.ModificationResponseDto;
import com.sparkplug.catalog.model.Modification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModificationMapper {

    @Mapping(target = "generationId", source = "generation.id")
    ModificationResponseDto toResponseDto(Modification m);
}
