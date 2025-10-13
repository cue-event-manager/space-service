package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.commands.UpdateSpaceTypeCommand;
import cue.edu.co.model.spacetype.exceptions.DuplicateSpaceTypeNameException;
import cue.edu.co.model.spacetype.exceptions.SpaceTypeNotFoundException;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSpaceTypeUseCase {
    private final SpaceTypeRepository spaceTypeRepository;

    public SpaceType execute(UpdateSpaceTypeCommand command) {
        SpaceType existingSpaceType = spaceTypeRepository.findById(command.id())
                .orElseThrow(() -> new SpaceTypeNotFoundException(command.id()));

        validateUniqueName(command.name(), command.id());

        SpaceType updatedSpaceType = command.toDomain(existingSpaceType);

        return spaceTypeRepository.save(updatedSpaceType);
    }

    private void validateUniqueName(String name, Long id) {
        if (spaceTypeRepository.existsByNameAndIdNot(name, id)) {
            throw new DuplicateSpaceTypeNameException(name);
        }
    }
}
