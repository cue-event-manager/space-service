package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.SpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpaceJpaRepository extends JpaRepository<SpaceEntity, Long>, JpaSpecificationExecutor<SpaceEntity> {
    boolean existsByNameAndCampusId(String name, Long campusId);
    boolean existsByNameAndCampusIdAndIdNot(String name, Long campusId, Long id);
}
