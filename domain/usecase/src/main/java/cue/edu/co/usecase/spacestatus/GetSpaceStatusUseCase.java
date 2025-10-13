package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.spacestatus.GetSpaceStatusQuery;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.exceptions.SpaceStatusNotFoundException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSpaceStatusUseCase {
    private final SpaceStatusRepository spaceStatusRepository;

    public SpaceStatus execute(GetSpaceStatusQuery query) {
        return spaceStatusRepository.findById(query.id())
                .orElseThrow(() -> new SpaceStatusNotFoundException(query.id()));
    }
}
