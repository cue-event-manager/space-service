package cue.edu.co.api.space.mappers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.common.mappers.OptionalMapper;
import cue.edu.co.api.common.mappers.PaginationDtoMapper;
import cue.edu.co.api.space.dtos.CreateSpaceRequestDto;
import cue.edu.co.api.space.dtos.SpacePaginationRequestDto;
import cue.edu.co.api.space.dtos.SpaceResponseDto;
import cue.edu.co.api.space.dtos.UpdateSpaceRequestDto;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.commands.CreateSpaceCommand;
import cue.edu.co.model.space.commands.UpdateSpaceCommand;
import cue.edu.co.model.space.queries.SpacePaginationQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OptionalMapper.class, PaginationDtoMapper.class})
public interface SpaceDtoMapper {

    CreateSpaceCommand toCommand(CreateSpaceRequestDto dto);

    @Mapping(target = "id", source = "id")
    UpdateSpaceCommand toCommand(Long id, UpdateSpaceRequestDto dto);

    @Mapping(target = "campus.id", source = "campus.id")
    @Mapping(target = "campus.name", source = "campus.name")
    @Mapping(target = "type.id", source = "type.id")
    @Mapping(target = "type.name", source = "type.name")
    @Mapping(target = "status.id", source = "status.id")
    @Mapping(target = "status.name", source = "status.name")
    SpaceResponseDto toDto(Space space);

    @Mapping(target = "pagination", source = "paginationRequestDto")
    SpacePaginationQuery toQuery(SpacePaginationRequestDto spacePaginationRequestDto, PaginationRequestDto paginationRequestDto);

    PaginationResponseDto<SpaceResponseDto> toDto(PageResult<Space> pageResult);
}
