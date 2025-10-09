package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.UpdateSpaceTypeCommand;
import cue.edu.co.model.spacetype.exceptions.DuplicateSpaceTypeNameException;
import cue.edu.co.model.spacetype.exceptions.SpaceTypeNotFoundException;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSpaceTypeUseCase {
    private final SpaceTypeRepository spaceTypeRepository;

    public SpaceType execute(UpdateSpaceTypeCommand command) {
        SpaceType existingSpaceType = spaceTypeRepository.findById(command.getId())
                .orElseThrow(() -> new SpaceTypeNotFoundException(command.getId()));

        validateUniqueName(command.getName(), command.getId());

        SpaceType updatedSpaceType = SpaceType.builder()
                .id(existingSpaceType.getId())
                .name(command.getName())
                .description(command.getDescription())
                .createdAt(existingSpaceType.getCreatedAt())
                .build();

        return spaceTypeRepository.save(updatedSpaceType);
    }

    private void validateUniqueName(String name, Long id) {
        if (spaceTypeRepository.existsByNameAndIdNot(name, id)) {
            throw new DuplicateSpaceTypeNameException(name);
        }
    }
}
