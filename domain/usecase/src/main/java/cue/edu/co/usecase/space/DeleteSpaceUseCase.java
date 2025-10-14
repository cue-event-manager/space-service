package cue.edu.co.usecase.space;

import cue.edu.co.model.space.commands.DeleteSpaceCommand;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.space.gateways.SpaceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteSpaceUseCase {
    private final SpaceRepository spaceRepository;

    public void execute(DeleteSpaceCommand command) {
        validateSpaceExists(command.id());

        spaceRepository.deleteById(command.id());
    }

    private void validateSpaceExists(Long id) {
        if (spaceRepository.findById(id).isEmpty()) {
            throw new SpaceNotFoundException(id);
        }
    }
}
