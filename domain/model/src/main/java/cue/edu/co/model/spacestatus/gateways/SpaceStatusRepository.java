package cue.edu.co.model.spacestatus.gateways;

import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.queries.SpaceStatusPaginationQuery;

import java.util.Optional;

public interface SpaceStatusRepository {
    SpaceStatus save(SpaceStatus spaceStatus);
    Optional<SpaceStatus> findById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    void deleteById(Long id);
    PageResult<SpaceStatus> findAllByFilters(SpaceStatusPaginationQuery query);
}
