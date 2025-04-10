package com.sparkplug.catalog.mapper;

import com.sparkplug.catalog.dto.modification.ModificationResponseDto;
import com.sparkplug.catalog.model.Modification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModificationMapper {

    ModificationResponseDto toResponseDto(Modification m);
}
