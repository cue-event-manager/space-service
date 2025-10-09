package cue.edu.co.jpa.mappers;

import cue.edu.co.jpa.entities.SpaceTypeEntity;
import cue.edu.co.model.spacetype.SpaceType;
import org.springframework.stereotype.Component;

@Component
public class SpaceTypeMapper {

    public SpaceTypeEntity toEntity(SpaceType spaceType) {
        if (spaceType == null) {
            return null;
        }

        return SpaceTypeEntity.builder()
                .id(spaceType.getId())
                .name(spaceType.getName())
                .description(spaceType.getDescription())
                .createdAt(spaceType.getCreatedAt())
                .build();
    }

    public SpaceType toDomain(SpaceTypeEntity entity) {
        if (entity == null) {
            return null;
        }

        return SpaceType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
