package cue.edu.co.model.campus.queries;

import cue.edu.co.model.common.queries.PaginationQuery;

import java.util.Optional;

public record CampusPaginationQuery(
        Optional<String> name,
        PaginationQuery pagination
) {
}
