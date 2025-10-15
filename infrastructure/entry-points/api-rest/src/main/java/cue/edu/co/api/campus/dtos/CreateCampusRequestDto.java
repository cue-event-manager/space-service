package cue.edu.co.api.campus.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static cue.edu.co.api.campus.constants.CampusValidation.*;
import static cue.edu.co.model.campus.constants.CampusConstant.*;

public record CreateCampusRequestDto(
        @NotBlank(message = NAME_REQUIRED)
        @Size(max = NAME_MAX_SIZE, message = NAME_SIZE)
        String name,

        @Size(max = ADDRESS_MAX_SIZE, message = ADDRESS_SIZE)
        String address
) {
}
