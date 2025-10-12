package cue.edu.co.jpa.specifications;

import cue.edu.co.jpa.entities.SpaceStatusEntity;
import cue.edu.co.model.spacestatus.queries.SpaceStatusPaginationQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static cue.edu.co.jpa.constants.SpaceStatusColumn.NAME;

public class SpaceStatusSpecificationBuilder extends AbstractSpecificationBuilder<SpaceStatusEntity, SpaceStatusPaginationQuery> {

    public SpaceStatusSpecificationBuilder(SpaceStatusPaginationQuery query) {
        super(query);
    }

    public static Specification<SpaceStatusEntity> build(SpaceStatusPaginationQuery query) {
        return new SpaceStatusSpecificationBuilder(query).build();
    }

    @Override
    protected void buildPredicates(List<Predicate> predicates, Root<SpaceStatusEntity> root, CriteriaBuilder cb) {
        addIfPresent(query.name(), predicates, name -> likeIgnoreCase(cb, root.get(NAME), name));
    }
}
