package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.commands.CreateSpaceTypeCommand;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.exceptions.DuplicateSpaceTypeNameException;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CreateSpaceTypeUseCase {
    private final SpaceTypeRepository spaceTypeRepository;

    public SpaceType execute(CreateSpaceTypeCommand command) {
        validateUniqueName(command.name());

        SpaceType spaceType = command.toDomain();

        return spaceTypeRepository.save(spaceType);
    }

    private void validateUniqueName(String name) {
        if (spaceTypeRepository.existsByName(name)) {
            throw new DuplicateSpaceTypeNameException(name);
        }
    }
}
