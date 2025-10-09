package cue.edu.co.jpa.adapters;

import cue.edu.co.jpa.entities.SpaceTypeEntity;
import cue.edu.co.jpa.mappers.SpaceTypeMapper;
import cue.edu.co.jpa.repositories.SpaceTypeJpaRepository;
import cue.edu.co.model.spacetype.GetSpaceTypePageQuery;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.SpaceTypePageResult;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public SpaceTypePageResult findAll(GetSpaceTypePageQuery query) {
        Pageable pageable = PageRequest.of(
                query.getPage() != null ? query.getPage() : 0,
                query.getSize() != null ? query.getSize() : 10
        );

        Page<SpaceTypeEntity> page;
        if (query.getName() != null && !query.getName().isEmpty()) {
            page = spaceTypeJpaRepository.findByNameContainingIgnoreCase(query.getName(), pageable);
        } else {
            page = spaceTypeJpaRepository.findAll(pageable);
        }

        List<SpaceType> content = page.getContent().stream()
                .map(spaceTypeMapper::toDomain)
                .collect(Collectors.toList());

        return SpaceTypePageResult.builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
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
