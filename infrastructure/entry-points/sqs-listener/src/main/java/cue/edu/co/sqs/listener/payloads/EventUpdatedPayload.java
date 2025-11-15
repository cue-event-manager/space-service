package cue.edu.co.sqs.listener.payloads;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventUpdatedPayload(
        Long eventId,
        Long spaceId,
        Long previousSpaceId,
        LocalDate date,
        LocalDate previousDate,
        LocalTime startTime,
        LocalTime endTime,
        boolean spaceChanged,
        boolean dateChanged,
        boolean timeChanged,
        boolean statusChanged
) {
}
