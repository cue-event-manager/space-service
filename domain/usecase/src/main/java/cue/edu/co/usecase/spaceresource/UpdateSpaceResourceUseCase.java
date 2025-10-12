package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.commands.UpdateSpaceResourceCommand;
import cue.edu.co.model.spaceresource.exceptions.DuplicateSpaceResourceNameException;
import cue.edu.co.model.spaceresource.exceptions.SpaceResourceNotFoundException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSpaceResourceUseCase {
    private final SpaceResourceRepository spaceResourceRepository;

    public SpaceResource execute(UpdateSpaceResourceCommand command) {
        SpaceResource existingSpaceResource = spaceResourceRepository.findById(command.id())
                .orElseThrow(() -> new SpaceResourceNotFoundException(command.id()));

        validateUniqueName(command.name(), command.id());

        SpaceResource updatedSpaceResource = command.toDomain(existingSpaceResource);

        return spaceResourceRepository.save(updatedSpaceResource);
    }

    private void validateUniqueName(String name, Long id) {
        if (spaceResourceRepository.existsByNameAndIdNot(name, id)) {
            throw new DuplicateSpaceResourceNameException(name);
        }
    }
}
