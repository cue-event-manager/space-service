package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.spaceresource.commands.CreateSpaceResourceCommand;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.exceptions.DuplicateSpaceResourceNameException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateSpaceResourceUseCase {
    private final SpaceResourceRepository spaceResourceRepository;

    public SpaceResource execute(CreateSpaceResourceCommand command) {
        validateUniqueName(command.name());

        SpaceResource spaceResource = command.toDomain();

        return spaceResourceRepository.save(spaceResource);
    }

    private void validateUniqueName(String name) {
        if (spaceResourceRepository.existsByName(name)) {
            throw new DuplicateSpaceResourceNameException(name);
        }
    }
}
