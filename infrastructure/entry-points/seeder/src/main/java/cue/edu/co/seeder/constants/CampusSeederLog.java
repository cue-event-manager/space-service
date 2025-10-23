package cue.edu.co.seeder.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampusSeederLog {
    STARTING("Starting campus seeder..."),
    CAMPUS_FOUND("Campus already exists: {}"),
    CAMPUS_CREATED("Campus created: {}"),
    FINISHED("Campus seeder finished.");

    private final String message;
}
