package cue.edu.co.model.spacereservation.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReserveSpaceCommand(
        Long spaceId,
        LocalDate date,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
