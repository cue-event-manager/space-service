package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.SpaceStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SpaceStatusJpaRepository extends JpaRepository<SpaceStatusEntity, Long>, JpaSpecificationExecutor<SpaceStatusEntity> {
    Optional<SpaceStatusEntity> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
