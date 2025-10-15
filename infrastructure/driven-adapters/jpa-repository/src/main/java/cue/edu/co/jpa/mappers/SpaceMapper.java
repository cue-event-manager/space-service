package cue.edu.co.jpa.mappers;

import cue.edu.co.jpa.entities.SpaceEntity;
import cue.edu.co.model.space.Space;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CampusMapper.class, SpaceTypeMapper.class, SpaceStatusMapper.class, SpaceResourceMapper.class})
public interface SpaceMapper {

    SpaceEntity toEntity(Space space);

    Space toDomain(SpaceEntity entity);
}
