package cue.edu.co.jpa.specifications;

import cue.edu.co.jpa.constants.SpaceResourceColumn;
import cue.edu.co.jpa.entities.SpaceResourceEntity;
import cue.edu.co.model.spaceresource.queries.SpaceResourcePaginationQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpaceResourceSpecificationBuilder extends AbstractSpecificationBuilder<SpaceResourceEntity, SpaceResourcePaginationQuery> {

    private SpaceResourceSpecificationBuilder(SpaceResourcePaginationQuery query) {
        super(query);
    }

    public static Specification<SpaceResourceEntity> build(SpaceResourcePaginationQuery query) {
        return new SpaceResourceSpecificationBuilder(query).build();
    }

    @Override
    protected void buildPredicates(List<Predicate> predicates,
                                   Root<SpaceResourceEntity> root,
                                   CriteriaBuilder cb) {

        addIfPresent(query.name(), predicates, name ->
                likeIgnoreCase(cb, root.get(SpaceResourceColumn.NAME), name)
        );
    }
}
