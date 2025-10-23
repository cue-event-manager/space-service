package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.queries.GetCampusQuery;
import cue.edu.co.model.campus.exceptions.CampusNotFoundException;
import cue.edu.co.model.campus.gateways.CampusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCampusUseCase {
    private final CampusRepository campusRepository;

    public Campus execute(GetCampusQuery query) {
        return campusRepository.findById(query.id())
                .orElseThrow(() -> new CampusNotFoundException(query.id()));
    }
}
