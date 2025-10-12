package cue.edu.co.api.spacestatus.mappers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.common.mappers.OptionalMapper;
import cue.edu.co.api.common.mappers.PaginationDtoMapper;
import cue.edu.co.api.spacestatus.dtos.CreateSpaceStatusRequestDto;
import cue.edu.co.api.spacestatus.dtos.SpaceStatusPaginationRequestDto;
import cue.edu.co.api.spacestatus.dtos.SpaceStatusResponseDto;
import cue.edu.co.api.spacestatus.dtos.UpdateSpaceStatusRequestDto;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.commands.CreateSpaceStatusCommand;
import cue.edu.co.model.spacestatus.commands.UpdateSpaceStatusCommand;
import cue.edu.co.model.spacestatus.queries.SpaceStatusPaginationQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { OptionalMapper.class, PaginationDtoMapper.class })
public interface SpaceStatusDtoMapper {

    CreateSpaceStatusCommand toCommand(CreateSpaceStatusRequestDto dto);

    @Mapping(target = "id", source = "id")
    UpdateSpaceStatusCommand toCommand(Long id, UpdateSpaceStatusRequestDto dto);

    SpaceStatusResponseDto toDto(SpaceStatus spaceStatus);

    @Mapping(target = "pagination", source = "paginationRequestDto")
    SpaceStatusPaginationQuery toQuery(SpaceStatusPaginationRequestDto spaceStatusPaginationRequestDto, PaginationRequestDto paginationRequestDto);

    PaginationResponseDto<SpaceStatusResponseDto> toDto(PageResult<SpaceStatus> spaceStatus);
}
