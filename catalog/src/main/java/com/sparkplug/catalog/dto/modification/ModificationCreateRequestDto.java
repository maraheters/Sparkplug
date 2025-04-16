package com.sparkplug.catalog.dto.modification;

public record ModificationCreateRequestDto(
        String name,
        Long generationId,
        DrivetrainCreateRequestDto drivetrain,
        EngineCreateRequestDto engine,
        TransmissionCreateRequestDto transmission
) {

    public record DrivetrainCreateRequestDto(String type){}

    public record EngineCreateRequestDto(
            String fuelType,
            String type,
            Integer horsepower,
            Integer torque){}

    public record TransmissionCreateRequestDto(String type, Integer numberOfGears) {}
}

