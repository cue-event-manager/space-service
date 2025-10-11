package cue.edu.co.model.spaceresource.exceptions;

import cue.edu.co.model.common.NotFoundException;

public class SpaceResourceNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Space resource not found";

    public SpaceResourceNotFoundException() {
        super(MESSAGE);
    }

    public SpaceResourceNotFoundException(Long id) {
        super(MESSAGE + " with id: " + id);
    }
}
