package cue.edu.co.model.space.commands;

import cue.edu.co.model.space.Space;

import java.time.LocalDateTime;
import java.util.Set;

public record CreateSpaceCommand(
        String name,
        Long campusId,
        Long typeId,
        Long statusId,
        Integer capacity,
        Set<Long> resourceIds
) {
    public Space toDomain() {
        return Space.builder()
                .name(name)
                .capacity(capacity)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
