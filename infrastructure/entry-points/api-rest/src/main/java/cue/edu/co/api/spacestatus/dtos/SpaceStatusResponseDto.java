package cue.edu.co.api.spacestatus.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record SpaceStatusResponseDto(
        Long id,
        String name,
        String description,
        Boolean canBeReserved,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) {
}
