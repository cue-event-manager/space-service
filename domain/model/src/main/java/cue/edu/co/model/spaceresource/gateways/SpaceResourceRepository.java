package cue.edu.co.model.spaceresource.gateways;

import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.queries.SpaceResourcePaginationQuery;

import java.util.List;
import java.util.Optional;

public interface SpaceResourceRepository {
    SpaceResource save(SpaceResource spaceResource);
    Optional<SpaceResource> findById(Long id);
    List<SpaceResource> findAll();
    Optional<SpaceResource> findByName(String name);
    void deleteById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    PageResult<SpaceResource> findAllByFilters(SpaceResourcePaginationQuery query);
}
