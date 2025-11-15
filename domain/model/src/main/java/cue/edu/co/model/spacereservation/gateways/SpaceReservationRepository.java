package cue.edu.co.model.spacereservation.gateways;

import cue.edu.co.model.spacereservation.SpaceReservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface SpaceReservationRepository {
    SpaceReservation save(SpaceReservation spaceReservation);
    Optional<SpaceReservation> findByEventId(Long id);
    boolean existsOverlappingReservation(Long spaceId, LocalDate date, LocalTime startTime, LocalTime endTime, Long eventIdToExclude);
    void deleteById(Long id);
}
