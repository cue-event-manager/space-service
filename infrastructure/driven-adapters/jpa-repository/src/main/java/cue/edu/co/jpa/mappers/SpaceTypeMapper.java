package cue.edu.co.jpa.mappers;

import cue.edu.co.jpa.entities.SpaceTypeEntity;
import cue.edu.co.model.spacetype.SpaceType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpaceTypeMapper {

    SpaceTypeEntity toEntity(SpaceType spaceType);

    SpaceType toDomain(SpaceTypeEntity entity);
}
