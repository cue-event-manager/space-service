package cue.edu.co.model.space.gateways;

import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.queries.GetAllSpacesQuery;
import cue.edu.co.model.space.queries.GetAvailableSpacesQuery;
import cue.edu.co.model.space.queries.SpacePaginationQuery;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SpaceRepository {
    Space save(Space space);
    Optional<Space> findById(Long id);
    void deleteById(Long id);
    boolean existsByNameAndCampusId(String name, Long campusId);
    boolean existsByNameAndCampusIdAndIdNot(String name, Long campusId, Long id);
    PageResult<Space> findAllByFilters(SpacePaginationQuery query);
    List<Space> findAllByIdIn(Set<Long> ids);
    List<Space> findAvailableSpaces(GetAvailableSpacesQuery query);
    List<Space> findAll(GetAllSpacesQuery getAllSpacesQuery);
}
