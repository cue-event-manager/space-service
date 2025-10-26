package cue.edu.co.api.space.constants;

public class SpaceValidation {
    private SpaceValidation() {
    }

    public static final String NAME_REQUIRED = "Name is required";
    public static final String NAME_MAX_LENGTH = "Name must not exceed 100 characters";
    public static final String CAMPUS_ID_REQUIRED = "Campus ID is required";
    public static final String TYPE_ID_REQUIRED = "Type ID is required";
    public static final String STATUS_ID_REQUIRED = "Status ID is required";
    public static final String CAPACITY_POSITIVE = "Capacity must be positive";

    public static final String EVENT_ID_REQUIRED = "Debe especificarse el ID del evento asociado.";
    public static final String DATE_REQUIRED = "Debe especificarse la fecha de reserva.";
    public static final String START_TIME_REQUIRED = "Debe especificarse la hora de inicio.";
    public static final String END_TIME_REQUIRED = "Debe especificarse la hora de finalización.";
}
