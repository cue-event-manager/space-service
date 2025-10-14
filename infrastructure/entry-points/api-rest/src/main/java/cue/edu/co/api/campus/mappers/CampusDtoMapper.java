package cue.edu.co.api.campus.mappers;

import cue.edu.co.api.campus.dtos.CampusPaginationRequestDto;
import cue.edu.co.api.campus.dtos.CampusResponseDto;
import cue.edu.co.api.campus.dtos.CreateCampusRequestDto;
import cue.edu.co.api.campus.dtos.UpdateCampusRequestDto;
import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.common.mappers.OptionalMapper;
import cue.edu.co.api.common.mappers.PaginationDtoMapper;
import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.commands.CreateCampusCommand;
import cue.edu.co.model.campus.commands.UpdateCampusCommand;
import cue.edu.co.model.campus.queries.CampusPaginationQuery;
import cue.edu.co.model.common.results.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OptionalMapper.class, PaginationDtoMapper.class})
public interface CampusDtoMapper {

    CreateCampusCommand toCommand(CreateCampusRequestDto dto);

    @Mapping(target = "id", source = "id")
    UpdateCampusCommand toCommand(Long id, UpdateCampusRequestDto dto);

    CampusResponseDto toDto(Campus campus);

    @Mapping(target = "pagination", source = "paginationRequestDto")
    CampusPaginationQuery toQuery(CampusPaginationRequestDto campusPaginationRequestDto, PaginationRequestDto paginationRequestDto);

    PaginationResponseDto<CampusResponseDto> toDto(PageResult<Campus> campus);
}
