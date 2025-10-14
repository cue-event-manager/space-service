package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusJpaRepository extends JpaRepository<CampusEntity, Long> {
}
