package cue.edu.co.model.spacereservation.commands;

import cue.edu.co.model.space.Space;
import cue.edu.co.model.spacereservation.SpaceReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReserveSpaceCommand(
        Long spaceId,
        Long eventId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {
    public SpaceReservation toDomain(Space space){
        return SpaceReservation.builder()
                .space(space)
                .eventId(eventId)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
