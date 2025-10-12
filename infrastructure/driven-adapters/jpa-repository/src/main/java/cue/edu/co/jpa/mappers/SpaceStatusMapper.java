package cue.edu.co.jpa.mappers;

import cue.edu.co.jpa.entities.SpaceStatusEntity;
import cue.edu.co.model.spacestatus.SpaceStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpaceStatusMapper {
    SpaceStatus toDomain(SpaceStatusEntity entity);
    SpaceStatusEntity toEntity(SpaceStatus domain);
}
