package cue.edu.co.model.spacereservation.gateways;

import cue.edu.co.model.spacereservation.SpaceReservation;

public interface SpaceReservationRepository {
    SpaceReservation save(SpaceReservation spaceReservation);
    void deleteById(Long id);
}
