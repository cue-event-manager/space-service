package cue.edu.co.api.spaceresource.mappers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.common.mappers.OptionalMapper;
import cue.edu.co.api.common.mappers.PaginationDtoMapper;
import cue.edu.co.api.spaceresource.dtos.CreateSpaceResourceRequestDto;
import cue.edu.co.api.spaceresource.dtos.SpaceResourcePaginationRequestDto;
import cue.edu.co.api.spaceresource.dtos.SpaceResourceResponseDto;
import cue.edu.co.api.spaceresource.dtos.UpdateSpaceResourceRequestDto;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spaceresource.CreateSpaceResourceCommand;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.UpdateSpaceResourceCommand;
import cue.edu.co.model.spaceresource.queries.SpaceResourcePaginationQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { OptionalMapper.class, PaginationDtoMapper.class })
public interface SpaceResourceDtoMapper {

    CreateSpaceResourceCommand toCommand(CreateSpaceResourceRequestDto dto);

    @Mapping(target = "id", source = "id")
    UpdateSpaceResourceCommand toCommand(Long id, UpdateSpaceResourceRequestDto dto);

    SpaceResourceResponseDto toDto(SpaceResource spaceResource);

    @Mapping(target = "pagination", source = "paginationRequestDto")
    SpaceResourcePaginationQuery toQuery(SpaceResourcePaginationRequestDto spaceResourcePaginationRequestDto, PaginationRequestDto paginationRequestDto);

    PaginationResponseDto<SpaceResourceResponseDto> toDto(PageResult<SpaceResource> spaceResource);
}
