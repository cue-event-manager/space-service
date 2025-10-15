package cue.edu.co.model.spacetype.gateways;

import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.queries.SpaceTypePaginationQuery;

import java.util.List;
import java.util.Optional;

public interface SpaceTypeRepository {
    SpaceType save(SpaceType spaceType);
    List<SpaceType> findAll();
    Optional<SpaceType> findById(Long id);
    Optional<SpaceType> findByName(String name);
    void deleteById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    PageResult<SpaceType> findAllByFilters(SpaceTypePaginationQuery query);
}
