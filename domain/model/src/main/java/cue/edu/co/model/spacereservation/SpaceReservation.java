package cue.edu.co.model.spacereservation;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class SpaceReservation {
    private Long id;
    private Long spaceId;
    private Long eventId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime createdAt;
}
