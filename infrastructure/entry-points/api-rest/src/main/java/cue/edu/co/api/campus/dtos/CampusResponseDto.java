package cue.edu.co.api.campus.dtos;

import java.time.LocalDateTime;

public record CampusResponseDto(
        Long id,
        String name,
        String address,
        LocalDateTime createdAt
) {
}
