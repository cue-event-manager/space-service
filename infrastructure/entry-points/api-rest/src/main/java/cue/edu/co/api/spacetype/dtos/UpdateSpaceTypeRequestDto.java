package cue.edu.co.api.spacetype.dtos;

import cue.edu.co.api.spacetype.constants.SpaceTypeValidation;
import cue.edu.co.model.spacetype.constants.SpaceTypeConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateSpaceTypeRequestDto(
        @NotBlank(message = SpaceTypeValidation.NAME_REQUIRED)
        @Size(max = SpaceTypeConstant.NAME_MAX_SIZE, message = SpaceTypeValidation.NAME_MAX_LENGTH)
        String name,

        @Size(max = SpaceTypeConstant.DESCRIPTION_MAX_SIZE, message = SpaceTypeValidation.DESCRIPTION_MAX_LENGTH)
        String description
) {
}
