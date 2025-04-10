package com.sparkplug.catalog.mapper;

import com.sparkplug.catalog.dto.generation.GenerationResponseDto;
import com.sparkplug.catalog.model.Generation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenerationMapper {

    GenerationResponseDto toDto(Generation g);
}
