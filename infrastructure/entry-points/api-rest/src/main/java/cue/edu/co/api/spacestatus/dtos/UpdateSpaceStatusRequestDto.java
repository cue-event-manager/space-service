package cue.edu.co.api.spacestatus.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static cue.edu.co.api.spacestatus.constants.SpaceStatusValidation.*;
import static cue.edu.co.model.spacestatus.constants.SpaceStatusConstant.*;

public record UpdateSpaceStatusRequestDto(
        @NotBlank(message = NAME_REQUIRED)
        @Size(max = NAME_MAX_SIZE, message = NAME_SIZE)
        String name,

        @Size(max = DESCRIPTION_MAX_SIZE, message = DESCRIPTION_SIZE)
        String description,

        @NotNull(message = CAN_BE_RESERVED_REQUIRED)
        Boolean canBeReserved
) {
}
