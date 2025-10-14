package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.mappers.CampusMapper;
import cue.edu.co.jpa.repositories.CampusJpaRepository;
import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.gateways.CampusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CampusRepositoryAdapter implements CampusRepository {
    private final CampusJpaRepository campusJpaRepository;
    private final CampusMapper campusMapper;

    @Override
    public Optional<Campus> findById(Long id) {
        return campusJpaRepository.findById(id)
                .map(campusMapper::toDomain);
    }
}
