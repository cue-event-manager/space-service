package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.queries.GetSpaceTypeQuery;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.exceptions.SpaceTypeNotFoundException;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSpaceTypeUseCase {
    private final SpaceTypeRepository spaceTypeRepository;

    public SpaceType execute(GetSpaceTypeQuery query) {
        return spaceTypeRepository.findById(query.id())
                .orElseThrow(() -> new SpaceTypeNotFoundException(query.id()));
    }
}
