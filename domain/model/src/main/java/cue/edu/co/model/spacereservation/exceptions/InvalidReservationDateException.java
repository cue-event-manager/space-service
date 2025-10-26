package cue.edu.co.model.spacereservation.exceptions;

import cue.edu.co.model.common.BusinessException;

public class InvalidReservationDateException  extends BusinessException {
    public InvalidReservationDateException() {
        super("No se pueden realizar reservas en fechas pasadas.", "INVALID_RESERVATION_DATE");
    }
}
