package cue.edu.co.api.spacestatus.constants;

public class SpaceStatusValidation {
    private SpaceStatusValidation() {}

    public static final String NAME_REQUIRED = "Space status name is required";
    public static final String NAME_SIZE = "Space status name must be between 1 and 100 characters";
    public static final String DESCRIPTION_SIZE = "Space status description must not exceed 255 characters";
    public static final String CAN_BE_RESERVED_REQUIRED = "Can be reserved field is required";
}
