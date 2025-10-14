package cue.edu.co.model.space.exceptions;

import cue.edu.co.model.common.NotFoundException;

public class SpaceNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Space not found";

    public SpaceNotFoundException() {
        super(MESSAGE);
    }

    public SpaceNotFoundException(Long id) {
        super(MESSAGE + " with id: " + id);
    }
}
