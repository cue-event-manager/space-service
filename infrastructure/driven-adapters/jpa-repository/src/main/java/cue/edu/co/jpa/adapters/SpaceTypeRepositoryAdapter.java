package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.entities.SpaceTypeEntity;
import cue.edu.co.jpa.mappers.PaginationMapper;
import cue.edu.co.jpa.mappers.SpaceTypeMapper;
import cue.edu.co.jpa.repositories.SpaceTypeJpaRepository;
import cue.edu.co.jpa.specifications.SpaceTypeSpecificationBuilder;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import cue.edu.co.model.spacetype.queries.SpaceTypePaginationQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpaceTypeRepositoryAdapter implements SpaceTypeRepository {
    private final SpaceTypeJpaRepository spaceTypeJpaRepository;
    private final SpaceTypeMapper spaceTypeMapper;
    private final PaginationMapper paginationMapper;

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

    @Override
    public PageResult<SpaceType> findAllByFilters(SpaceTypePaginationQuery query) {
        Pageable pageable = paginationMapper.toPageable(query.pagination());

        Specification<SpaceTypeEntity> specification = SpaceTypeSpecificationBuilder.build(query);

        Page<SpaceTypeEntity> page = spaceTypeJpaRepository.findAll(specification, pageable);

        return paginationMapper.toPageResult(page, spaceTypeMapper::toDomain);
    }
}
