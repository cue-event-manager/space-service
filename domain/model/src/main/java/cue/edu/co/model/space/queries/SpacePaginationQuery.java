package cue.edu.co.model.space.queries;

import cue.edu.co.model.common.queries.PaginationQuery;

import java.util.Optional;

public record SpacePaginationQuery(
        Optional<String> name,
        Optional<Long> campusId,
        Optional<Long> typeId,
        Optional<Long> statusId,
        PaginationQuery pagination
) {
}
