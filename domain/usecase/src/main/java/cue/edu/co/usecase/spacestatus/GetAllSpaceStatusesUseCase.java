package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.queries.SpaceStatusPaginationQuery;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllSpaceStatusesUseCase {
    private final SpaceStatusRepository spaceStatusRepository;

    public PageResult<SpaceStatus> execute(SpaceStatusPaginationQuery query) {
        return spaceStatusRepository.findAllByFilters(query);
    }
}
