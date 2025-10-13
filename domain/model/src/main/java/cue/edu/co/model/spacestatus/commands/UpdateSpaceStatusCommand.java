package cue.edu.co.model.spacestatus.commands;

import cue.edu.co.model.spacestatus.SpaceStatus;

public record UpdateSpaceStatusCommand(
        Long id,
        String name,
        String description,
        Boolean canBeReserved
) {
    public SpaceStatus toDomain(SpaceStatus existing) {
        return SpaceStatus.builder()
                .id(existing.getId())
                .name(name)
                .description(description)
                .canBeReserved(canBeReserved)
                .createdAt(existing.getCreatedAt())
                .build();
    }
}
