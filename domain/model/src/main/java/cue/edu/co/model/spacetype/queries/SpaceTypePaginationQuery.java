package cue.edu.co.model.spacetype.queries;

import cue.edu.co.model.common.queries.PaginationQuery;
import lombok.Builder;

import java.util.Optional;

@Builder
public record SpaceTypePaginationQuery(
        Optional<String> name,
        PaginationQuery pagination
) {
}
