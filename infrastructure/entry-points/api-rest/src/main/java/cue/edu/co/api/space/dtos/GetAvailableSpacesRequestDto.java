package cue.edu.co.api.space.dtos;

import cue.edu.co.api.space.constants.SpaceValidation;
import cue.edu.co.model.event.enums.RecurrenceType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AvailableSpacesRequestDto(
        Integer minCapacity,
        LocalDate date,
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime,
        RecurrenceType recurrenceType,
        Long campusId,
        Long typeId
) {
}
