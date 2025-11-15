package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.SpaceReservationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface SpaceReservationJpaRepository extends CrudRepository<SpaceReservationEntity, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM SpaceReservationEntity r
        WHERE r.spaceId = :spaceId
          AND r.date = :date
          AND r.startTime < :endTime
          AND r.endTime > :startTime
    """)
    boolean existsOverlappingReservation(
            @Param("spaceId") Long spaceId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    Optional<SpaceReservationEntity> findByEventId(Long eventId);
}
