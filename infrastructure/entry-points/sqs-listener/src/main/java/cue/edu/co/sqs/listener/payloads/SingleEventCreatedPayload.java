package cue.edu.co.sqs.listener.payloads;

import java.time.LocalDate;
import java.time.LocalTime;

public record SingleEventCreatedPayload(
        Long eventId,
        Long spaceId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime

) {
}
