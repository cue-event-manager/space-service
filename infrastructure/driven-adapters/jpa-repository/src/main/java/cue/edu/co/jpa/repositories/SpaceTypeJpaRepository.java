package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.SpaceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceTypeJpaRepository extends JpaRepository<SpaceTypeEntity, Long> {
    Optional<SpaceTypeEntity> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
