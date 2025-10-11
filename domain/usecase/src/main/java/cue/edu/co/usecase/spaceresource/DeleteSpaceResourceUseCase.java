package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.spaceresource.DeleteSpaceResourceCommand;
import cue.edu.co.model.spaceresource.exceptions.SpaceResourceNotFoundException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteSpaceResourceUseCase {
    private final SpaceResourceRepository spaceResourceRepository;

    public void execute(DeleteSpaceResourceCommand command) {
        spaceResourceRepository.findById(command.id())
                .orElseThrow(() -> new SpaceResourceNotFoundException(command.id()));

        spaceResourceRepository.deleteById(command.id());
    }
}
