package cue.edu.co.api.space.dtos;

import cue.edu.co.model.event.enums.RecurrenceType;

import java.time.LocalDate;
import java.time.LocalTime;

public record GetAvailableSpacesRequestDto(
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
