package cue.edu.co.model.space.queries;

import cue.edu.co.model.event.enums.RecurrenceType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record GetAvailableSpacesQuery(
        Optional<Integer> minCapacity,
        Optional<LocalDate> date,
        Optional<LocalDate> startDate,
        Optional<LocalDate> endDate,
        Optional<LocalTime> startTime,
        Optional<LocalTime> endTime,
        Optional<RecurrenceType> recurrenceType,
        Optional<Long> campusId,
        Optional<Long> typeId
){}
