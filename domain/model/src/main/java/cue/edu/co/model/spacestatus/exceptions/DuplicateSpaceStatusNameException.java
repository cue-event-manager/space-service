package cue.edu.co.model.spacestatus.exceptions;

import cue.edu.co.model.common.BusinessException;

public class DuplicateSpaceStatusNameException extends BusinessException {
    private static final String MESSAGE = "Space status name already exists";
    private static final String CODE = "SPACE_STATUS_DUPLICATE_NAME";

    public DuplicateSpaceStatusNameException() {
        super(MESSAGE, CODE);
    }

    public DuplicateSpaceStatusNameException(String name) {
        super(MESSAGE + ": " + name, CODE);
    }
}
