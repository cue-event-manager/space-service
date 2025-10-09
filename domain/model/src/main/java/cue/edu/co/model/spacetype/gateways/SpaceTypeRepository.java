package cue.edu.co.model.spacetype.gateways;

import cue.edu.co.model.spacetype.SpaceType;

import java.util.Optional;

public interface SpaceTypeRepository {
    SpaceType save(SpaceType spaceType);
    Optional<SpaceType> findById(Long id);
    Optional<SpaceType> findByName(String name);
    void deleteById(Long id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
