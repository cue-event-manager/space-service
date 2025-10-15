package cue.edu.co.model.space.exceptions;

import cue.edu.co.model.common.BusinessException;

public class DuplicateSpaceNameException extends BusinessException {
    private static final String MESSAGE = "Space name already exists in this campus";
    private static final String CODE = "SPACE_DUPLICATE_NAME";

    public DuplicateSpaceNameException() {
        super(MESSAGE, CODE);
    }

    public DuplicateSpaceNameException(String name) {
        super(MESSAGE + ": " + name, CODE);
    }
}
