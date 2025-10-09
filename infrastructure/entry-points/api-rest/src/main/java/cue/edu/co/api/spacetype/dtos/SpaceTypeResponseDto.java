package cue.edu.co.api.spacetype.dtos;

import java.time.LocalDateTime;

public record SpaceTypeResponseDto(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {
}
