package cue.edu.co.model.spacetype.commands;

import cue.edu.co.model.spacetype.SpaceType;
import java.time.LocalDateTime;

public record CreateSpaceTypeCommand(
        String name,
        String description
) {
    public SpaceType toDomain() {
        return SpaceType.builder()
                .name(name)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
