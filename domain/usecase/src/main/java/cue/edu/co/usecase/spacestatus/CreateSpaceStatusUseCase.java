package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.commands.CreateSpaceStatusCommand;
import cue.edu.co.model.spacestatus.exceptions.DuplicateSpaceStatusNameException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateSpaceStatusUseCase {
    private final SpaceStatusRepository spaceStatusRepository;

    public SpaceStatus execute(CreateSpaceStatusCommand command) {
        if (spaceStatusRepository.existsByName(command.name())) {
            throw new DuplicateSpaceStatusNameException(command.name());
        }

        SpaceStatus spaceStatus = command.toDomain();
        return spaceStatusRepository.save(spaceStatus);
    }
}
