package cue.edu.co.model.campus.commands;

import cue.edu.co.model.campus.Campus;

public record UpdateCampusCommand(
        Long id,
        String name,
        String address
) {
    public Campus toDomain(Campus existing) {
        return Campus.builder()
                .id(existing.getId())
                .name(name)
                .address(address)
                .createdAt(existing.getCreatedAt())
                .build();
    }
}
