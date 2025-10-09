package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.entities.SpaceTypeEntity;
import cue.edu.co.jpa.mappers.SpaceTypeMapper;
import cue.edu.co.jpa.repositories.SpaceTypeJpaRepository;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpaceTypeRepositoryAdapter implements SpaceTypeRepository {
    private final SpaceTypeJpaRepository spaceTypeJpaRepository;
    private final SpaceTypeMapper spaceTypeMapper;

    @Override
    public SpaceType save(SpaceType spaceType) {
        SpaceTypeEntity entity = spaceTypeMapper.toEntity(spaceType);
        SpaceTypeEntity savedEntity = spaceTypeJpaRepository.save(entity);
        return spaceTypeMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<SpaceType> findById(Long id) {
        return spaceTypeJpaRepository.findById(id)
                .map(spaceTypeMapper::toDomain);
    }

    @Override
    public Optional<SpaceType> findByName(String name) {
        return spaceTypeJpaRepository.findByName(name)
                .map(spaceTypeMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        spaceTypeJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return spaceTypeJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return spaceTypeJpaRepository.existsByNameAndIdNot(name, id);
    }
}
