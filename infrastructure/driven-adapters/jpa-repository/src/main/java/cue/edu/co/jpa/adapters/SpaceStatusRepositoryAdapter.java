package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.entities.SpaceStatusEntity;
import cue.edu.co.jpa.mappers.PaginationMapper;
import cue.edu.co.jpa.mappers.SpaceStatusMapper;
import cue.edu.co.jpa.repositories.SpaceStatusJpaRepository;
import cue.edu.co.jpa.specifications.SpaceStatusSpecificationBuilder;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import cue.edu.co.model.spacestatus.queries.SpaceStatusPaginationQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpaceStatusRepositoryAdapter implements SpaceStatusRepository {
    private final SpaceStatusJpaRepository spaceStatusJpaRepository;
    private final SpaceStatusMapper spaceStatusMapper;
    private final PaginationMapper paginationMapper;

    @Override
    public SpaceStatus save(SpaceStatus spaceStatus) {
        SpaceStatusEntity entity = spaceStatusMapper.toEntity(spaceStatus);
        SpaceStatusEntity savedEntity = spaceStatusJpaRepository.save(entity);
        return spaceStatusMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<SpaceStatus> findById(Long id) {
        return spaceStatusJpaRepository.findById(id)
                .map(spaceStatusMapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return spaceStatusJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return spaceStatusJpaRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public void deleteById(Long id) {
        spaceStatusJpaRepository.deleteById(id);
    }

    @Override
    public PageResult<SpaceStatus> findAllByFilters(SpaceStatusPaginationQuery query) {
        Pageable pageable = paginationMapper.toPageable(query.pagination());
        Specification<SpaceStatusEntity> spec = SpaceStatusSpecificationBuilder.build(query);
        Page<SpaceStatusEntity> page = spaceStatusJpaRepository.findAll(spec, pageable);
        return paginationMapper.toPageResult(page, spaceStatusMapper::toDomain);
    }
}
