package cue.edu.co.model.campus.gateways;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.queries.CampusPaginationQuery;
import cue.edu.co.model.common.results.PageResult;

import java.util.Optional;

public interface CampusRepository {
    Campus save(Campus campus);

    Optional<Campus> findById(Long id);

    PageResult<Campus> findAllByFilters(CampusPaginationQuery query);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteById(Long id);
}
