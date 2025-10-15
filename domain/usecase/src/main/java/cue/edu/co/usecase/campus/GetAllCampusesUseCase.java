package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.model.campus.queries.CampusPaginationQuery;
import cue.edu.co.model.common.results.PageResult;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllCampusesUseCase {
    private final CampusRepository campusRepository;

    public PageResult<Campus> execute(CampusPaginationQuery query) {
        return campusRepository.findAllByFilters(query);
    }

    public List<Campus> execute(){
        return campusRepository.findAll();
    }
 }
