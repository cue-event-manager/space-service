package cue.edu.co.model.spacereservation;
import cue.edu.co.model.space.Space;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class SpaceReservation {
    private Long id;
    private Space space;
    private Long eventId;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
}
