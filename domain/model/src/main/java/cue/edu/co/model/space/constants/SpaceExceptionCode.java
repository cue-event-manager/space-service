package cue.edu.co.model.space.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceExceptionCode {

    MISSING_DATE_OR_RANGE(
            "Debe especificar una fecha única o un rango de fechas para la búsqueda.",
            "AVAILABLE_SPACES_MISSING_DATE_OR_RANGE"
    ),
    CONFLICTING_DATE_PARAMETERS(
            "No se puede combinar una fecha única con un rango de fechas (startDate / endDate).",
            "AVAILABLE_SPACES_CONFLICTING_DATE_PARAMETERS"
    ),
    MISSING_RECURRENCE_DATA(
            "Debe especificar tanto la fecha de inicio como la de finalización para un evento recurrente.",
            "AVAILABLE_SPACES_MISSING_RECURRENCE_DATA"
    ),
    INVALID_DATE_RANGE(
            "La fecha de inicio no puede ser posterior a la fecha de finalización.",
            "AVAILABLE_SPACES_INVALID_DATE_RANGE"
    ),
    MISSING_RECURRENCE_TYPE(
            "Debe especificar el tipo de recurrencia para eventos recurrentes.",
            "AVAILABLE_SPACES_MISSING_RECURRENCE_TYPE"
    ),
    MISSING_TIME_RANGE(
            "Debe especificar la hora de inicio y la hora de finalización.",
            "AVAILABLE_SPACES_MISSING_TIME_RANGE"
    ),
    INVALID_TIME_RANGE(
            "La hora de inicio no puede ser posterior a la hora de finalización.",
            "AVAILABLE_SPACES_INVALID_TIME_RANGE"
    );

    private final String message;
    private final String code;
}
