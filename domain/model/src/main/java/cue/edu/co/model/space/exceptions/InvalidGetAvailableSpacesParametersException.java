package cue.edu.co.model.space.exceptions;

import cue.edu.co.model.common.BusinessException;
import cue.edu.co.model.space.constants.SpaceExceptionCode;

public class InvalidGetAvailableSpacesParametersException extends BusinessException {
    public InvalidGetAvailableSpacesParametersException(SpaceExceptionCode code) {
        super(code.getMessage(), code.getCode());
    }
}