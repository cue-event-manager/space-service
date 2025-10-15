package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CampusJpaRepository extends JpaRepository<CampusEntity, Long>, JpaSpecificationExecutor<CampusEntity> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
