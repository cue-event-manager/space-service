package cue.edu.co.model.spaceresource.commands;

import cue.edu.co.model.spaceresource.SpaceResource;

public record UpdateSpaceResourceCommand(
        Long id,
        String name,
        String description
) {
    public SpaceResource toDomain(SpaceResource existing) {
        return SpaceResource.builder()
                .id(existing.getId())
                .name(name)
                .description(description)
                .createdAt(existing.getCreatedAt())
                .build();
    }
}
