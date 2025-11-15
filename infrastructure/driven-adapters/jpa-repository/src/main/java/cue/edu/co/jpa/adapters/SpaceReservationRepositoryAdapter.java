package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.mappers.SpaceReservationMapper;
import cue.edu.co.jpa.repositories.SpaceReservationJpaRepository;
import cue.edu.co.model.spacereservation.SpaceReservation;
import cue.edu.co.model.spacereservation.gateways.SpaceReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpaceReservationRepositoryAdapter implements SpaceReservationRepository {
    private final SpaceReservationJpaRepository spaceReservationJpaRepository;
    private final SpaceReservationMapper spaceReservationMapper;

    @Transactional
    @Override
    public SpaceReservation save(SpaceReservation spaceReservation) {
        return spaceReservationMapper.toDomain(
                spaceReservationJpaRepository.save(
                        spaceReservationMapper.toEntity(spaceReservation)
                )
        );
    }

    @Override
    public Optional<SpaceReservation> findByEventId(Long id) {
        return spaceReservationJpaRepository
                .findByEventId(id)
                .map(spaceReservationMapper::toDomain);
    }

    @Override
    public boolean existsOverlappingReservation(Long spaceId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return spaceReservationJpaRepository.existsOverlappingReservation(spaceId,date,startTime,endTime);
    }

    @Override
    public void deleteById(Long id) {
        spaceReservationJpaRepository.deleteById(id);
    }
}
