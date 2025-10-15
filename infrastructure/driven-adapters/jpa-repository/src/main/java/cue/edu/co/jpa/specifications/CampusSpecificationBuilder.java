package cue.edu.co.jpa.specifications;

import cue.edu.co.jpa.entities.CampusEntity;
import cue.edu.co.model.campus.queries.CampusPaginationQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static cue.edu.co.jpa.constants.CampusColumn.NAME;

public class CampusSpecificationBuilder extends AbstractSpecificationBuilder<CampusEntity, CampusPaginationQuery> {

    public CampusSpecificationBuilder(CampusPaginationQuery query) {
        super(query);
    }

    public static Specification<CampusEntity> build(CampusPaginationQuery query) {
        return new CampusSpecificationBuilder(query).build();
    }

    @Override
    protected void buildPredicates(List<Predicate> predicates, Root<CampusEntity> root, CriteriaBuilder cb) {
        addIfPresent(query.name(), predicates, name -> likeIgnoreCase(cb, root.get(NAME), name));
    }
}
