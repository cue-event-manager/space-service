package cue.edu.co.model.spacereservation.commands;

import java.time.LocalDate;
import java.time.LocalTime;

public record ValidateSpaceAvailabilityCommand(
        Long spaceId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        Long eventIdToExclude
) {}