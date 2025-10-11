package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.model.spaceresource.queries.SpaceResourcePaginationQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllSpaceResourcesUseCase {
    private final SpaceResourceRepository spaceResourceRepository;

    public PageResult<SpaceResource> execute(SpaceResourcePaginationQuery query) {
        return spaceResourceRepository.findAllByFilters(query);
    }
}
