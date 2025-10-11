package cue.edu.co.api.spaceresource.dtos;

import cue.edu.co.api.spaceresource.constants.SpaceResourceValidation;
import cue.edu.co.model.spaceresource.constants.SpaceResourceConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateSpaceResourceRequestDto(
        @NotBlank(message = SpaceResourceValidation.NAME_REQUIRED)
        @Size(max = SpaceResourceConstant.NAME_MAX_SIZE, message = SpaceResourceValidation.NAME_MAX_LENGTH)
        String name,

        @Size(max = SpaceResourceConstant.DESCRIPTION_MAX_SIZE, message = SpaceResourceValidation.DESCRIPTION_MAX_LENGTH)
        String description
) {
}
