package cue.edu.co.model.campus.gateways;

import cue.edu.co.model.campus.Campus;

import java.util.Optional;

public interface CampusRepository {
    Optional<Campus> findById(Long id);
}
