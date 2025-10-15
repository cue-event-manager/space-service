package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.entities.CampusEntity;
import cue.edu.co.jpa.mappers.CampusMapper;
import cue.edu.co.jpa.mappers.PaginationMapper;
import cue.edu.co.jpa.repositories.CampusJpaRepository;
import cue.edu.co.jpa.specifications.CampusSpecificationBuilder;
import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.model.campus.queries.CampusPaginationQuery;
import cue.edu.co.model.common.results.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CampusRepositoryAdapter implements CampusRepository {
    private final CampusJpaRepository campusJpaRepository;
    private final CampusMapper campusMapper;
    private final PaginationMapper paginationMapper;

    @Override
    public Campus save(Campus campus) {
        CampusEntity entity = campusMapper.toEntity(campus);
        CampusEntity savedEntity = campusJpaRepository.save(entity);
        return campusMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Campus> findById(Long id) {
        return campusJpaRepository.findById(id)
                .map(campusMapper::toDomain);
    }

    @Override
    public PageResult<Campus> findAllByFilters(CampusPaginationQuery query) {
        Pageable pageable = paginationMapper.toPageable(query.pagination());
        Specification<CampusEntity> spec = CampusSpecificationBuilder.build(query);
        Page<CampusEntity> page = campusJpaRepository.findAll(spec, pageable);
        return paginationMapper.toPageResult(page, campusMapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return campusJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return campusJpaRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public void deleteById(Long id) {
        campusJpaRepository.deleteById(id);
    }
}
