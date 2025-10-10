package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import cue.edu.co.model.spacetype.queries.SpaceTypePaginationQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllSpaceTypesUseCase {
    private final SpaceTypeRepository spaceTypeRepository;

    public PageResult<SpaceType> execute(SpaceTypePaginationQuery query) {
        return spaceTypeRepository.findAllByFilters(query);
    }
}
