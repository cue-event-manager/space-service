package cue.edu.co.model.spacereservation.gateways;

import cue.edu.co.model.spacereservation.SpaceReservation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SpaceReservationRepository {
    SpaceReservation save(SpaceReservation spaceReservation);
    boolean existsOverlappingReservation(Long spaceId, LocalDate date, LocalTime startTime, LocalTime endTime);
    void deleteById(Long id);
}
