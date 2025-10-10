package cue.edu.co.jpa.mappers;

import cue.edu.co.jpa.entities.SpaceResourceEntity;
import cue.edu.co.model.spaceresource.SpaceResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpaceResourceMapper {

    SpaceResourceEntity toEntity(SpaceResource spaceResource);

    SpaceResource toDomain(SpaceResourceEntity entity);
}
