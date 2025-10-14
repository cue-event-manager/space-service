package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.commands.CreateCampusCommand;
import cue.edu.co.model.campus.exceptions.DuplicateCampusNameException;
import cue.edu.co.model.campus.gateways.CampusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCampusUseCase {
    private final CampusRepository campusRepository;

    public Campus execute(CreateCampusCommand command) {
        if (campusRepository.existsByName(command.name())) {
            throw new DuplicateCampusNameException(command.name());
        }

        Campus campus = command.toDomain();
        return campusRepository.save(campus);
    }
}
