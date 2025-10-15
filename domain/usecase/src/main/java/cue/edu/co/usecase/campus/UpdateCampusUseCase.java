package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.commands.UpdateCampusCommand;
import cue.edu.co.model.campus.exceptions.CampusNotFoundException;
import cue.edu.co.model.campus.exceptions.DuplicateCampusNameException;
import cue.edu.co.model.campus.gateways.CampusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateCampusUseCase {
    private final CampusRepository campusRepository;

    public Campus execute(UpdateCampusCommand command) {
        Campus existingCampus = campusRepository.findById(command.id())
                .orElseThrow(() -> new CampusNotFoundException(command.id()));

        if (campusRepository.existsByNameAndIdNot(command.name(), command.id())) {
            throw new DuplicateCampusNameException(command.name());
        }

        Campus updatedCampus = command.toDomain(existingCampus);
        return campusRepository.save(updatedCampus);
    }
}
