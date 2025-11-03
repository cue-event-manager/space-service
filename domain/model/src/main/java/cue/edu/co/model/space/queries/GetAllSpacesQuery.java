package cue.edu.co.model.space.queries;

import java.util.Optional;

public record GetAllSpacesQuery(
        Optional<String> name,
        Optional<Long> campusId,
        Optional<Long> typeId,
        Optional<Long> statusId
) {
}
