package cue.edu.co.model.spacereservation.exceptions;

import cue.edu.co.model.common.BusinessException;

public class SpaceReservationNotFoundException extends BusinessException {
    public SpaceReservationNotFoundException() {
        super(
                "No se encontró la reserva del espacio",
                "SPACE_RESERVATION_NOT_FOUND"
        );
    }
}
