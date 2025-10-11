package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.spaceresource.GetSpaceResourceQuery;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.exceptions.SpaceResourceNotFoundException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetSpaceResourceUseCase {
    private final SpaceResourceRepository spaceResourceRepository;

    public SpaceResource execute(GetSpaceResourceQuery query) {
        return spaceResourceRepository.findById(query.id())
                .orElseThrow(() -> new SpaceResourceNotFoundException(query.id()));
    }
}
