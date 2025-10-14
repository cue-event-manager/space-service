package cue.edu.co.api.space.dtos;

import cue.edu.co.api.space.constants.SpaceValidation;
import cue.edu.co.model.space.constants.SpaceConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateSpaceRequestDto(
        @NotBlank(message = SpaceValidation.NAME_REQUIRED)
        @Size(max = SpaceConstant.NAME_MAX_SIZE, message = SpaceValidation.NAME_MAX_LENGTH)
        String name,

        @NotNull(message = SpaceValidation.CAMPUS_ID_REQUIRED)
        Long campusId,

        @NotNull(message = SpaceValidation.TYPE_ID_REQUIRED)
        Long typeId,

        @NotNull(message = SpaceValidation.STATUS_ID_REQUIRED)
        Long statusId,

        @Positive(message = SpaceValidation.CAPACITY_POSITIVE)
        Integer capacity,

        Set<Long> resourceIds
) {
}
