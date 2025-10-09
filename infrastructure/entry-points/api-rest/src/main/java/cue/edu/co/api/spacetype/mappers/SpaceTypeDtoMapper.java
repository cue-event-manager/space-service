package cue.edu.co.api.spacetype.mappers;

import cue.edu.co.api.spacetype.dtos.CreateSpaceTypeRequestDto;
import cue.edu.co.api.spacetype.dtos.SpaceTypeResponseDto;
import cue.edu.co.api.spacetype.dtos.UpdateSpaceTypeRequestDto;
import cue.edu.co.model.spacetype.CreateSpaceTypeCommand;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.UpdateSpaceTypeCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpaceTypeDtoMapper {

    CreateSpaceTypeCommand toCommand(CreateSpaceTypeRequestDto dto);

    @Mapping(target = "id", source = "id")
    UpdateSpaceTypeCommand toCommand(Long id, UpdateSpaceTypeRequestDto dto);

    SpaceTypeResponseDto toDto(SpaceType spaceType);
}
