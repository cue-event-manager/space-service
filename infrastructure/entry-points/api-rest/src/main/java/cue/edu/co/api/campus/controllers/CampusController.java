package cue.edu.co.api.campus.controllers;

import cue.edu.co.api.campus.constants.CampusEndpoint;
import cue.edu.co.api.campus.dtos.CampusPaginationRequestDto;
import cue.edu.co.api.campus.dtos.CampusResponseDto;
import cue.edu.co.api.campus.dtos.CreateCampusRequestDto;
import cue.edu.co.api.campus.dtos.UpdateCampusRequestDto;
import cue.edu.co.api.campus.mappers.CampusDtoMapper;
import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.queries.GetCampusQuery;
import cue.edu.co.model.campus.commands.CreateCampusCommand;
import cue.edu.co.model.campus.commands.DeleteCampusCommand;
import cue.edu.co.model.campus.commands.UpdateCampusCommand;
import cue.edu.co.model.campus.queries.CampusPaginationQuery;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.usecase.campus.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CampusController {
    private final CreateCampusUseCase createCampusUseCase;
    private final UpdateCampusUseCase updateCampusUseCase;
    private final DeleteCampusUseCase deleteCampusUseCase;
    private final GetCampusUseCase getCampusUseCase;
    private final GetAllCampusesUseCase getAllCampusesUseCase;
    private final CampusDtoMapper campusDtoMapper;

    @PostMapping(CampusEndpoint.CAMPUS_CREATE_ENDPOINT)
    public ResponseEntity<CampusResponseDto> create(@Valid @RequestBody CreateCampusRequestDto request) {
        CreateCampusCommand command = campusDtoMapper.toCommand(request);
        Campus campus = createCampusUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(campusDtoMapper.toDto(campus));
    }

    @GetMapping(CampusEndpoint.CAMPUS_BASE)
    public ResponseEntity<PaginationResponseDto<CampusResponseDto>> getAll(
            @Valid CampusPaginationRequestDto requestDto,
            @Valid PaginationRequestDto paginationRequestDto
    ) {
        CampusPaginationQuery query = campusDtoMapper.toQuery(requestDto, paginationRequestDto);
        PageResult<Campus> pageResult = getAllCampusesUseCase.execute(query);
        PaginationResponseDto<CampusResponseDto> response = campusDtoMapper.toDto(pageResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping(CampusEndpoint.CAMPUS_GET_ALL_ENDPOINT)
    public ResponseEntity<List<CampusResponseDto>> getAll() {
        List<CampusResponseDto> campuses = getAllCampusesUseCase
                .execute()
                .stream()
                .map(campusDtoMapper::toDto)
                .toList();

        return ResponseEntity.ok(campuses);
    }

    @GetMapping(CampusEndpoint.CAMPUS_BY_ID)
    public ResponseEntity<CampusResponseDto> getById(@PathVariable Long id) {
        GetCampusQuery query = new GetCampusQuery(id);
        Campus campus = getCampusUseCase.execute(query);
        return ResponseEntity.ok(campusDtoMapper.toDto(campus));
    }

    @PutMapping(CampusEndpoint.CAMPUS_UPDATE_ENDPOINT)
    public ResponseEntity<CampusResponseDto> update(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateCampusRequestDto request) {
        UpdateCampusCommand command = campusDtoMapper.toCommand(id, request);
        Campus campus = updateCampusUseCase.execute(command);
        return ResponseEntity.ok(campusDtoMapper.toDto(campus));
    }

    @DeleteMapping(CampusEndpoint.CAMPUS_DELETE_ENDPOINT)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        DeleteCampusCommand command = new DeleteCampusCommand(id);
        deleteCampusUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }
}
