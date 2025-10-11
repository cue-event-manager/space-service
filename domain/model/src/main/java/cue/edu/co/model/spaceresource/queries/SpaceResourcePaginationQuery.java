package cue.edu.co.model.spaceresource.queries;

import cue.edu.co.model.common.queries.PaginationQuery;
import lombok.Builder;

import java.util.Optional;

@Builder
public record SpaceResourcePaginationQuery(
        Optional<String> name,
        PaginationQuery pagination
) {
}
