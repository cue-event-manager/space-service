package cue.edu.co.model.spacereservation;
import cue.edu.co.model.space.Space;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class SpaceReservation {
    private Long id;
    private Space space;
    private Long eventId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime createdAt;
}
