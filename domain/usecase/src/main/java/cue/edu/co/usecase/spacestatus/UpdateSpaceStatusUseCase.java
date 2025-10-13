package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.commands.UpdateSpaceStatusCommand;
import cue.edu.co.model.spacestatus.exceptions.DuplicateSpaceStatusNameException;
import cue.edu.co.model.spacestatus.exceptions.SpaceStatusNotFoundException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateSpaceStatusUseCase {
    private final SpaceStatusRepository spaceStatusRepository;

    public SpaceStatus execute(UpdateSpaceStatusCommand command) {
        SpaceStatus existing = spaceStatusRepository.findById(command.id())
                .orElseThrow(() -> new SpaceStatusNotFoundException(command.id()));

        if (spaceStatusRepository.existsByNameAndIdNot(command.name(), command.id())) {
            throw new DuplicateSpaceStatusNameException(command.name());
        }

        SpaceStatus updated = command.toDomain(existing);
        return spaceStatusRepository.save(updated);
    }
}
