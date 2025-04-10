package com.sparkplug.catalog.mapper;

import com.sparkplug.catalog.dto.carModel.CarModelResponseDto;
import com.sparkplug.catalog.model.CarModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarModelMapper {

    CarModelResponseDto toResponseDto(CarModel c);
}
