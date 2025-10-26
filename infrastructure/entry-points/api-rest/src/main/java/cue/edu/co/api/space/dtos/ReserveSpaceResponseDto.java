package cue.edu.co.api.space.dtos;


import java.time.LocalDate;
import java.time.LocalTime;

public record ReserveSpaceResponseDto(
        Long reservationId,
        Long spaceId,
        Long eventId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {}
