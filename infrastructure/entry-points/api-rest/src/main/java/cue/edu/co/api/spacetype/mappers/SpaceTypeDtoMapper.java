package cue.edu.co.api.spacetype.mappers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.common.mappers.OptionalMapper;
import cue.edu.co.api.common.mappers.PaginationDtoMapper;
import cue.edu.co.api.spacetype.dtos.CreateSpaceTypeRequestDto;
import cue.edu.co.api.spacetype.dtos.SpaceTypePaginationRequestDto;
import cue.edu.co.api.spacetype.dtos.SpaceTypeResponseDto;
import cue.edu.co.api.spacetype.dtos.UpdateSpaceTypeRequestDto;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacetype.CreateSpaceTypeCommand;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.UpdateSpaceTypeCommand;
import cue.edu.co.model.spacetype.queries.SpaceTypePaginationQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { OptionalMapper.class, PaginationDtoMapper.class })
public interface SpaceTypeDtoMapper {

    CreateSpaceTypeCommand toCommand(CreateSpaceTypeRequestDto dto);

    @Mapping(target = "id", source = "id")
    UpdateSpaceTypeCommand toCommand(Long id, UpdateSpaceTypeRequestDto dto);

    SpaceTypeResponseDto toDto(SpaceType spaceType);

    @Mapping(target = "pagination", source = "paginationRequestDto")
    SpaceTypePaginationQuery toQuery(SpaceTypePaginationRequestDto spaceTypePaginationRequestDto, PaginationRequestDto paginationRequestDto);

    PaginationResponseDto<SpaceTypeResponseDto> toDto(PageResult<SpaceType> spaceType);
}
