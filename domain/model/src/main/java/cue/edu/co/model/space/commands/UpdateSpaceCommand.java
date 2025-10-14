package cue.edu.co.model.space.commands;

import cue.edu.co.model.space.Space;

import java.util.Set;

public record UpdateSpaceCommand(
        Long id,
        String name,
        Long campusId,
        Long typeId,
        Long statusId,
        Integer capacity,
        Set<Long> resourceIds
) {
    public Space toDomain(Space existing) {
        return Space.builder()
                .id(existing.getId())
                .name(name)
                .capacity(capacity)
                .createdAt(existing.getCreatedAt())
                .build();
    }
}
