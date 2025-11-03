package cue.edu.co.jpa.specifications;

import cue.edu.co.jpa.entities.SpaceEntity;
import cue.edu.co.model.space.queries.GetAllSpacesQuery;
import cue.edu.co.model.space.queries.SpacePaginationQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static cue.edu.co.jpa.constants.SpaceColumn.*;
import static cue.edu.co.jpa.constants.SpaceColumn.ID;
import static cue.edu.co.jpa.constants.SpaceColumn.STATUS;
import static cue.edu.co.jpa.constants.SpaceColumn.TYPE;

public class GetAllSpacesSpecificationBuilder extends AbstractSpecificationBuilder<SpaceEntity, GetAllSpacesQuery> {

    private GetAllSpacesSpecificationBuilder(GetAllSpacesQuery query) {
        super(query);
    }

    public static Specification<SpaceEntity> build(GetAllSpacesQuery query) {
        return new GetAllSpacesSpecificationBuilder(query).build();
    }

    @Override
    protected void buildPredicates(List<Predicate> predicates,
                                   Root<SpaceEntity> root,
                                   CriteriaBuilder cb) {

        addIfPresent(query.name(), predicates, name ->
                likeIgnoreCase(cb, root.get(NAME), name)
        );

        addIfPresent(query.campusId(), predicates, campusId ->
                cb.equal(root.get(CAMPUS).get(ID), campusId)
        );

        addIfPresent(query.typeId(), predicates, typeId ->
                cb.equal(root.get(TYPE).get(ID), typeId)
        );

        addIfPresent(query.statusId(), predicates, statusId ->
                cb.equal(root.get(STATUS).get(ID), statusId)
        );
    }
}
