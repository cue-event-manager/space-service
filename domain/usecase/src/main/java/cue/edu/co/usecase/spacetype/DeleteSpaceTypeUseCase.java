package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.DeleteSpaceTypeCommand;
import cue.edu.co.model.spacetype.exceptions.SpaceTypeNotFoundException;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteSpaceTypeUseCase {
    private final SpaceTypeRepository spaceTypeRepository;

    public void execute(DeleteSpaceTypeCommand command) {
        if (!spaceTypeRepository.findById(command.id()).isPresent()) {
            throw new SpaceTypeNotFoundException(command.id());
        }

        spaceTypeRepository.deleteById(command.id());
    }
}
