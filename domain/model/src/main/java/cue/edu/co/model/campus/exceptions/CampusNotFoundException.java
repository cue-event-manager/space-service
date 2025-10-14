package cue.edu.co.model.campus.exceptions;

import cue.edu.co.model.common.NotFoundException;

public class CampusNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Campus not found";

    public CampusNotFoundException() {
        super(MESSAGE);
    }

    public CampusNotFoundException(Long id) {
        super(MESSAGE + " with id: " + id);
    }
}
