package cue.edu.co.seeder.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceStatusSeederLog {
    STARTING("Starting space status seeder..."),
    STATUS_EXISTS("Space status already exists: {}"),
    STATUS_CREATED("Space status created: {}"),
    FINISHED("Space status seeder finished.");

    private final String message;
}
