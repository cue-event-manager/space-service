package cue.edu.co.jpa.repositories;

import cue.edu.co.jpa.entities.SpaceTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceTypeJpaRepository extends JpaRepository<SpaceTypeEntity, Long> {
    Optional<SpaceTypeEntity> findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    Page<SpaceTypeEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
