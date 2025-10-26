package cue.edu.co.api.space.dtos;

import cue.edu.co.api.space.constants.SpaceValidation;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ValidateSpaceAvailabilityRequestDto(

        @NotNull(message = SpaceValidation.DATE_REQUIRED)
        LocalDate date,

        @NotNull(message = SpaceValidation.START_TIME_REQUIRED)
        LocalTime startTime,

        @NotNull(message = SpaceValidation.END_TIME_REQUIRED)
        LocalTime endTime
) {}