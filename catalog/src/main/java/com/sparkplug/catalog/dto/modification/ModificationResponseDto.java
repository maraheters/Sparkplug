package com.sparkplug.catalog.dto.modification;

public record ModificationResponseDto(
        Long id,
        String name,
        Long generationId,
        DrivetrainDto drivetrain,
        EngineDto engine,
        TransmissionDto transmission
) {

    public record DrivetrainDto(Long id, String type){}

    public record EngineDto(
            Long id,
            String fuelType,
            String type,
            Integer horsepower,
            Integer torque){}

    public record TransmissionDto(Long id, String type, Integer numberOfGears) {}
}
