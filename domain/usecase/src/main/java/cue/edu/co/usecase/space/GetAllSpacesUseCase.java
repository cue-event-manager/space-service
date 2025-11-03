package cue.edu.co.usecase.space;

import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.space.queries.GetAllSpacesQuery;
import cue.edu.co.model.space.queries.SpacePaginationQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllSpacesUseCase {
    private final SpaceRepository spaceRepository;

    public PageResult<Space> execute(SpacePaginationQuery query) {
        return spaceRepository.findAllByFilters(query);
    }

    public List<Space> execute(GetAllSpacesQuery getAllSpacesQuery) {
        return spaceRepository.findAll(getAllSpacesQuery);
    }
}
