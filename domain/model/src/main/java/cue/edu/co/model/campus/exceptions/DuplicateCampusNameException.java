package cue.edu.co.model.campus.exceptions;

import cue.edu.co.model.common.BusinessException;

public class DuplicateCampusNameException extends BusinessException {
    private static final String MESSAGE = "Campus name already exists";
    private static final String CODE = "CAMPUS_DUPLICATE_NAME";

    public DuplicateCampusNameException() {
        super(MESSAGE, CODE);
    }

    public DuplicateCampusNameException(String name) {
        super(MESSAGE + ": " + name, CODE);
    }
}
