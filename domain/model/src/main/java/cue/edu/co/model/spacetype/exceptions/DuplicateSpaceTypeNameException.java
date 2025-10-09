package cue.edu.co.model.spacetype.exceptions;

import cue.edu.co.model.common.BusinessException;

public class DuplicateSpaceTypeNameException extends BusinessException {
    private static final String MESSAGE = "Space type name already exists";
    private static final String CODE = "SPACE_TYPE_DUPLICATE_NAME";

    public DuplicateSpaceTypeNameException() {
        super(MESSAGE, CODE);
    }

    public DuplicateSpaceTypeNameException(String name) {
        super(MESSAGE + ": " + name, CODE);
    }
}
