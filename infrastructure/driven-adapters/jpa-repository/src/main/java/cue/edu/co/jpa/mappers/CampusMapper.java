package cue.edu.co.jpa.mappers;

import cue.edu.co.jpa.entities.CampusEntity;
import cue.edu.co.model.campus.Campus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CampusMapper {
    Campus toDomain(CampusEntity entity);

    CampusEntity toEntity(Campus domain);
}
