package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.entities.SpaceResourceEntity;
import cue.edu.co.jpa.mappers.PaginationMapper;
import cue.edu.co.jpa.mappers.SpaceResourceMapper;
import cue.edu.co.jpa.repositories.SpaceResourceJpaRepository;
import cue.edu.co.jpa.specifications.SpaceResourceSpecificationBuilder;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.model.spaceresource.queries.SpaceResourcePaginationQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpaceResourceRepositoryAdapter implements SpaceResourceRepository {
    private final SpaceResourceJpaRepository spaceResourceJpaRepository;
    private final SpaceResourceMapper spaceResourceMapper;
    private final PaginationMapper paginationMapper;

    @Override
    public SpaceResource save(SpaceResource spaceResource) {
        SpaceResourceEntity entity = spaceResourceMapper.toEntity(spaceResource);
        SpaceResourceEntity savedEntity = spaceResourceJpaRepository.save(entity);
        return spaceResourceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<SpaceResource> findById(Long id) {
        return spaceResourceJpaRepository.findById(id)
                .map(spaceResourceMapper::toDomain);
    }

    @Override
    public Optional<SpaceResource> findByName(String name) {
        return spaceResourceJpaRepository.findByName(name)
                .map(spaceResourceMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        spaceResourceJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return spaceResourceJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return spaceResourceJpaRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public PageResult<SpaceResource> findAllByFilters(SpaceResourcePaginationQuery query) {
        Pageable pageable = paginationMapper.toPageable(query.pagination());

        Specification<SpaceResourceEntity> specification = SpaceResourceSpecificationBuilder.build(query);

        Page<SpaceResourceEntity> page = spaceResourceJpaRepository.findAll(specification, pageable);

        return paginationMapper.toPageResult(page, spaceResourceMapper::toDomain);
    }
}
