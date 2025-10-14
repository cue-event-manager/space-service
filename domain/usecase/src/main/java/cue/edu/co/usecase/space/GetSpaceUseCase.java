package cue.edu.co.usecase.space;

import cue.edu.co.model.space.GetSpaceQuery;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.space.gateways.SpaceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSpaceUseCase {
    private final SpaceRepository spaceRepository;

    public Space execute(GetSpaceQuery query) {
        return spaceRepository.findById(query.id())
                .orElseThrow(() -> new SpaceNotFoundException(query.id()));
    }
}
