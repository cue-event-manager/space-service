package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.SpaceResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SpaceResourceJpaRepository extends JpaRepository<SpaceResourceEntity, Long>, JpaSpecificationExecutor<SpaceResourceEntity> {
    Optional<SpaceResourceEntity> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
