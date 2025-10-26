package cue.edu.co.model.spacereservation.exceptions;

import cue.edu.co.model.common.BusinessException;

public class SpaceNotReservableException extends BusinessException {

    public SpaceNotReservableException(String spaceName) {
        super("El espacio " + spaceName + " no está disponible para reservas.", "SPACE_NOT_RESERVABLE");
    }
}
