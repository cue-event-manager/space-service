package cue.edu.co.api.space.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public record SpaceResponseDto(
        Long id,
        String name,
        Integer capacity,
        CampusInfoDto campus,
        SpaceTypeInfoDto type,
        SpaceStatusInfoDto status,
        Set<SpaceResourceInfoDto> resources,
        LocalDateTime createdAt
) {
    public record CampusInfoDto(Long id, String name) {
    }

    public record SpaceTypeInfoDto(Long id, String name) {
    }

    public record SpaceStatusInfoDto(Long id, String name) {
    }

    public record SpaceResourceInfoDto(Long id, String name) {
    }
}
