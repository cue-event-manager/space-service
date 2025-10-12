package cue.edu.co.model.spacestatus.queries;

import cue.edu.co.model.common.queries.PaginationQuery;

import java.util.Optional;

public record SpaceStatusPaginationQuery(
        Optional<String> name,
        PaginationQuery pagination
) {
}
