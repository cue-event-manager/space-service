package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.SpaceReservationEntity;
import org.springframework.data.repository.CrudRepository;

public interface SpaceReservationJpaRepository extends CrudRepository<SpaceReservationEntity, Long> {
}
