package cue.edu.co.api.spacetype.constants;

public class SpaceTypeValidation {
    private SpaceTypeValidation() {}

    public static final String NAME_REQUIRED = "Name is required";
    public static final String NAME_MAX_LENGTH = "Name must not exceed 100 characters";
    public static final String DESCRIPTION_MAX_LENGTH = "Description must not exceed 255 characters";

    // Constraints
    public static final int NAME_MAX_SIZE = 100;
    public static final int DESCRIPTION_MAX_SIZE = 255;
}
