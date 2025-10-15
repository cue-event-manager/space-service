package cue.edu.co.model.campus.commands;

import cue.edu.co.model.campus.Campus;

import java.time.LocalDateTime;

public record CreateCampusCommand(
        String name,
        String address
) {
    public Campus toDomain() {
        return Campus.builder()
                .name(name)
                .address(address)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
