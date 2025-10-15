package cue.edu.co.api.space.dtos;

public record SpacePaginationRequestDto(
        String name,
        Long campusId,
        Long typeId,
        Long statusId
) {
}
