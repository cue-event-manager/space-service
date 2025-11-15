package cue.edu.co.model.spacereservation.commands;

import cue.edu.co.model.spacereservation.SpaceReservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReserveSpaceCommand(
        Long spaceId,
        Long eventId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {
    public SpaceReservation toDomain(){
        return SpaceReservation.builder()
                .spaceId(spaceId)
                .eventId(eventId)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
