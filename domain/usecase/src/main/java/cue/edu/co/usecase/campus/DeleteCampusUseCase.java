package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.commands.DeleteCampusCommand;
import cue.edu.co.model.campus.exceptions.CampusNotFoundException;
import cue.edu.co.model.campus.gateways.CampusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteCampusUseCase {
    private final CampusRepository campusRepository;

    public void execute(DeleteCampusCommand command) {
        if (!campusRepository.findById(command.id()).isPresent()) {
            throw new CampusNotFoundException(command.id());
        }

        campusRepository.deleteById(command.id());
    }
}
