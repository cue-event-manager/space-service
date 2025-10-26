package cue.edu.co.model.spacereservation.exceptions;

import cue.edu.co.model.common.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;

public class SpaceAlreadyReservedException extends BusinessException {
    public SpaceAlreadyReservedException(LocalDate date, LocalTime start, LocalTime end) {
        super(String.format(
                "El espacio ya está reservado el %s entre %s y %s.",
                 date, start, end
        ), "SPACE_ALREADY_RESERVED");
    }
}