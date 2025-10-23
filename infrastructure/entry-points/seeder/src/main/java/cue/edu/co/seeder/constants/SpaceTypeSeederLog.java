package cue.edu.co.seeder.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceTypeSeederLog {
    STARTING("Starting space type seeder..."),
    TYPE_EXISTS("Space type already exists: {}"),
    TYPE_CREATED("Space type created: {}"),
    FINISHED("Space type seeder finished.");

    private final String message;
}
