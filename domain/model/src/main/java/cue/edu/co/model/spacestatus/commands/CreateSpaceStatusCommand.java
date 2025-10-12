package cue.edu.co.model.spacestatus.commands;

import cue.edu.co.model.spacestatus.SpaceStatus;

import java.time.LocalDateTime;

public record CreateSpaceStatusCommand(
        String name,
        String description,
        Boolean canBeReserved
) {
    public SpaceStatus toDomain() {
        return SpaceStatus.builder()
                .name(name)
                .description(description)
                .canBeReserved(canBeReserved)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
