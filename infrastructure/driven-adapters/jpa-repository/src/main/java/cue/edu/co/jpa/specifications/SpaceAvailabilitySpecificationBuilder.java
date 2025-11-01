package cue.edu.co.jpa.specifications;


import cue.edu.co.jpa.constants.SpaceColumn;
import cue.edu.co.jpa.entities.SpaceEntity;
import cue.edu.co.jpa.entities.SpaceReservationEntity;
import cue.edu.co.model.space.queries.GetAvailableSpacesQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.List;

import static cue.edu.co.jpa.constants.SpaceColumn.*;
import static cue.edu.co.jpa.constants.SpaceReservationColumn.*;
import static cue.edu.co.jpa.constants.SpaceReservationColumn.ID;
import static cue.edu.co.jpa.constants.SpaceStatusColumn.CAN_BE_RESERVED;


public class SpaceAvailabilitySpecificationBuilder
        extends AbstractSpecificationBuilder<SpaceEntity, GetAvailableSpacesQuery> {

    private SpaceAvailabilitySpecificationBuilder(GetAvailableSpacesQuery query) {
        super(query);
    }

    public static Specification<SpaceEntity> build(GetAvailableSpacesQuery query) {
        return new SpaceAvailabilitySpecificationBuilder(query).build();
    }

    @Override
    protected void buildPredicates(List<Predicate> predicates,
                                   Root<SpaceEntity> root,
                                   CriteriaBuilder cb) {

        predicates.add(canBeReservedPredicate(root, cb));

        addIfPresent(query.minCapacity(), predicates, minCap ->
                cb.greaterThanOrEqualTo(root.get(CAPACITY), minCap)
        );

        addIfPresent(query.campusId(), predicates, campusId ->
                cb.equal(root.get(CAMPUS).get(SpaceColumn.ID), campusId)
        );

        addIfPresent(query.typeId(), predicates, typeId ->
                cb.equal(root.get(TYPE).get(SpaceColumn.ID), typeId)
        );

        predicates.add(excludeOverlappingSpacesPredicate(root, cb));
    }

    private Predicate canBeReservedPredicate(Root<SpaceEntity> root, CriteriaBuilder cb) {
        return cb.isTrue(root.get(STATUS).get(CAN_BE_RESERVED));
    }

    private Predicate excludeOverlappingSpacesPredicate(Root<SpaceEntity> root, CriteriaBuilder cb) {
        Subquery<Long> overlappingSubquery = buildOverlappingReservationsSubquery(root, cb);
        return cb.not(root.get(ID).in(overlappingSubquery));
    }

    private Subquery<Long> buildOverlappingReservationsSubquery(Root<SpaceEntity> root, CriteriaBuilder cb) {
        CriteriaQuery<?> mainQuery = cb.createQuery();
        Subquery<Long> subquery = mainQuery.subquery(Long.class);

        Root<SpaceReservationEntity> reservation = subquery.from(SpaceReservationEntity.class);
        subquery.select(reservation.get(SPACE).get(SpaceColumn.ID));

        Predicate overlapConditions = buildOverlapConditions(reservation, cb);
        subquery.where(overlapConditions);

        return subquery;
    }

    private Predicate buildOverlapConditions(Root<SpaceReservationEntity> reservation, CriteriaBuilder cb) {
        Predicate dateCondition = buildDateCondition(reservation, cb);
        Predicate timeCondition = buildTimeCondition(reservation, cb);
        return cb.and(dateCondition, timeCondition);
    }

    private Predicate buildDateCondition(Root<SpaceReservationEntity> reservation, CriteriaBuilder cb) {
        if (query.date().isPresent()) {
            return cb.equal(reservation.get(DATE), query.date().get());
        }

        if (query.startDate().isPresent() && query.endDate().isPresent()) {
            return cb.between(
                    reservation.get(DATE),
                    query.startDate().get(),
                    query.endDate().get()
            );
        }

        return cb.disjunction();
    }

    private Predicate buildTimeCondition(Root<SpaceReservationEntity> reservation, CriteriaBuilder cb) {
        LocalTime startTime = query.startTime().orElseThrow();
        LocalTime endTime = query.endTime().orElseThrow();

        return cb.and(
                cb.lessThan(reservation.get(START_TIME), endTime),
                cb.greaterThan(reservation.get(END_TIME), startTime)
        );
    }
}