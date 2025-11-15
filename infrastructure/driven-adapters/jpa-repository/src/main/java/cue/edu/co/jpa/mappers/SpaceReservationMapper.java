package cue.edu.co.jpa.mappers;

import cue.edu.co.jpa.entities.SpaceReservationEntity;
import cue.edu.co.model.spacereservation.SpaceReservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpaceReservationMapper {

    SpaceReservationEntity toEntity(SpaceReservation space);

    SpaceReservation toDomain(SpaceReservationEntity entity);
}
