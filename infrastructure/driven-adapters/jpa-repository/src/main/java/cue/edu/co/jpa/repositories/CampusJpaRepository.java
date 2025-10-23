package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CampusJpaRepository extends JpaRepository<CampusEntity, Long>, JpaSpecificationExecutor<CampusEntity> {
    Optional<CampusEntity> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
