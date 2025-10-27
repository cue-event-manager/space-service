package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.entities.SpaceEntity;
import cue.edu.co.jpa.mappers.PaginationMapper;
import cue.edu.co.jpa.mappers.SpaceMapper;
import cue.edu.co.jpa.repositories.SpaceJpaRepository;
import cue.edu.co.jpa.specifications.SpaceSpecificationBuilder;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.space.queries.SpacePaginationQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class SpaceRepositoryAdapter implements SpaceRepository {
    private final SpaceJpaRepository spaceJpaRepository;
    private final SpaceMapper spaceMapper;
    private final PaginationMapper paginationMapper;

    @Override
    public Space save(Space space) {
        SpaceEntity entity = spaceMapper.toEntity(space);
        SpaceEntity savedEntity = spaceJpaRepository.save(entity);
        return spaceMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public Optional<Space> findById(Long id) {
        return spaceJpaRepository.findById(id)
                .map(spaceMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        spaceJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNameAndCampusId(String name, Long campusId) {
        return spaceJpaRepository.existsByNameAndCampusId(name, campusId);
    }

    @Override
    public boolean existsByNameAndCampusIdAndIdNot(String name, Long campusId, Long id) {
        return spaceJpaRepository.existsByNameAndCampusIdAndIdNot(name, campusId, id);
    }

    @Override
    public PageResult<Space> findAllByFilters(SpacePaginationQuery query) {
        Pageable pageable = paginationMapper.toPageable(query.pagination());

        Specification<SpaceEntity> specification = SpaceSpecificationBuilder.build(query);

        Page<SpaceEntity> page = spaceJpaRepository.findAll(specification, pageable);

        return paginationMapper.toPageResult(page, spaceMapper::toDomain);
    }

    @Override
    public List<Space> findAllByIdIn(Set<Long> ids) {
        return spaceJpaRepository.findAllById(ids).stream()
                .map(spaceMapper::toDomain)
                .toList();
    }
}
