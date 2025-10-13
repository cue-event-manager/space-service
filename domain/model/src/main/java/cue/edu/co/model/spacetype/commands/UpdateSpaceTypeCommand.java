package cue.edu.co.model.spacetype.commands;

import cue.edu.co.model.spacetype.SpaceType;

public record UpdateSpaceTypeCommand(
        Long id,
        String name,
        String description
) {
    public SpaceType toDomain(SpaceType existing) {
        return SpaceType.builder()
                .id(existing.getId())
                .name(name)
                .description(description)
                .createdAt(existing.getCreatedAt())
                .build();
    }
}
