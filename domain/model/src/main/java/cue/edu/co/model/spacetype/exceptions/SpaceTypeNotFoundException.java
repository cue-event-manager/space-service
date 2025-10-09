package cue.edu.co.model.spacetype.exceptions;

import cue.edu.co.model.common.NotFoundException;

public class SpaceTypeNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Space type not found";

    public SpaceTypeNotFoundException() {
        super(MESSAGE);
    }

    public SpaceTypeNotFoundException(Long id) {
        super(MESSAGE + " with id: " + id);
    }
}
