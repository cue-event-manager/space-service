package cue.edu.co.api.spaceresource.dtos;

import java.time.LocalDateTime;

public record SpaceResourceResponseDto(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {
}
