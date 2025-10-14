package cue.edu.co.api.campus.constants;

public class CampusValidation {
    private CampusValidation() {
    }

    public static final String NAME_REQUIRED = "Campus name is required";
    public static final String NAME_SIZE = "Campus name must be between 1 and 100 characters";
    public static final String ADDRESS_SIZE = "Campus address must not exceed 255 characters";
}
