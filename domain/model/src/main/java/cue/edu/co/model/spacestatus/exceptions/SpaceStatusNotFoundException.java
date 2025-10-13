package cue.edu.co.model.spacestatus.exceptions;

import cue.edu.co.model.common.NotFoundException;

public class SpaceStatusNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Space status not found";

    public SpaceStatusNotFoundException() {
        super(MESSAGE);
    }

    public SpaceStatusNotFoundException(Long id) {
        super(MESSAGE + " with id: " + id);
    }
}
