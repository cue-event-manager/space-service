package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.spacestatus.commands.DeleteSpaceStatusCommand;
import cue.edu.co.model.spacestatus.exceptions.SpaceStatusNotFoundException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteSpaceStatusUseCase {
    private final SpaceStatusRepository spaceStatusRepository;

    public void execute(DeleteSpaceStatusCommand command) {
        spaceStatusRepository.findById(command.id())
                .orElseThrow(() -> new SpaceStatusNotFoundException(command.id()));

        spaceStatusRepository.deleteById(command.id());
    }
}
