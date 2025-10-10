package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.SpaceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SpaceTypeJpaRepository extends JpaRepository<SpaceTypeEntity, Long>, JpaSpecificationExecutor<SpaceTypeEntity> {
    Optional<SpaceTypeEntity> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
