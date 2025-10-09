package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.GetSpaceTypePageQuery;
import cue.edu.co.model.spacetype.SpaceTypePageResult;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSpaceTypePageUseCase {
    private final SpaceTypeRepository spaceTypeRepository;

    public SpaceTypePageResult execute(GetSpaceTypePageQuery query) {
        return spaceTypeRepository.findAll(query);
    }
}
