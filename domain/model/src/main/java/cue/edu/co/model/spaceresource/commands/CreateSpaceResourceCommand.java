package cue.edu.co.model.spaceresource.commands;

import cue.edu.co.model.spaceresource.SpaceResource;
import java.time.LocalDateTime;

public record CreateSpaceResourceCommand(
        String name,
        String description
) {
    public SpaceResource toDomain() {
        return SpaceResource.builder()
                .name(name)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
