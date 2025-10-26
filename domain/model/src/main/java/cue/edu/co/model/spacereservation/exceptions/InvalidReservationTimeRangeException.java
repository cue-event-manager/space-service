package cue.edu.co.model.spacereservation.exceptions;

import cue.edu.co.model.common.BusinessException;

public class InvalidReservationTimeRangeException extends BusinessException {
    public InvalidReservationTimeRangeException() {
        super("La hora de inicio debe ser anterior a la hora de fin.", "INVALID_RESERVATION_TIME_RANGE");
    }
}
