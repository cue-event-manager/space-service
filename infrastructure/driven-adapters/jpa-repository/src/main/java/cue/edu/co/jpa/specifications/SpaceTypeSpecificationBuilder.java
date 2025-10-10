package cue.edu.co.jpa.specifications;

import cue.edu.co.jpa.constants.SpaceTypeColumn;
import cue.edu.co.jpa.entities.SpaceTypeEntity;
import cue.edu.co.model.spacetype.queries.SpaceTypePaginationQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpaceTypeSpecificationBuilder extends AbstractSpecificationBuilder<SpaceTypeEntity, SpaceTypePaginationQuery> {

    private SpaceTypeSpecificationBuilder(SpaceTypePaginationQuery query) {
        super(query);
    }

    public static Specification<SpaceTypeEntity> build(SpaceTypePaginationQuery query) {
        return new SpaceTypeSpecificationBuilder(query).build();
    }

    @Override
    protected void buildPredicates(List<Predicate> predicates,
                                   Root<SpaceTypeEntity> root,
                                   CriteriaBuilder cb) {

        addIfPresent(query.name(), predicates, name ->
                likeIgnoreCase(cb, root.get(SpaceTypeColumn.NAME), name)
        );
    }
}
