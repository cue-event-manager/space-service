package cue.edu.co.model.spaceresource.exceptions;

import cue.edu.co.model.common.BusinessException;

public class DuplicateSpaceResourceNameException extends BusinessException {
    private static final String MESSAGE = "Space resource name already exists";
    private static final String CODE = "SPACE_RESOURCE_DUPLICATE_NAME";

    public DuplicateSpaceResourceNameException() {
        super(MESSAGE, CODE);
    }

    public DuplicateSpaceResourceNameException(String name) {
        super(MESSAGE + ": " + name, CODE);
    }
}
