package cue.edu.co.seeder.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceResourceSeederLog {
    STARTING("Starting space resource seeder..."),
    RESOURCE_EXISTS("Space resource already exists: {}"),
    RESOURCE_CREATED("Space resource created: {}"),
    FINISHED("Space resource seeder finished.");

    private final String message;
}
