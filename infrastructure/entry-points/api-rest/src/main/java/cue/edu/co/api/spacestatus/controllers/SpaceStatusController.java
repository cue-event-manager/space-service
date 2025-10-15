package cue.edu.co.api.spacestatus.controllers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.spacestatus.constants.SpaceStatusEndpoint;
import cue.edu.co.api.spacestatus.dtos.CreateSpaceStatusRequestDto;
import cue.edu.co.api.spacestatus.dtos.SpaceStatusPaginationRequestDto;
import cue.edu.co.api.spacestatus.dtos.SpaceStatusResponseDto;
import cue.edu.co.api.spacestatus.dtos.UpdateSpaceStatusRequestDto;
import cue.edu.co.api.spacestatus.mappers.SpaceStatusDtoMapper;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacestatus.GetSpaceStatusQuery;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.commands.CreateSpaceStatusCommand;
import cue.edu.co.model.spacestatus.commands.DeleteSpaceStatusCommand;
import cue.edu.co.model.spacestatus.commands.UpdateSpaceStatusCommand;
import cue.edu.co.model.spacestatus.queries.SpaceStatusPaginationQuery;
import cue.edu.co.usecase.spacestatus.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceStatusController {
    private final CreateSpaceStatusUseCase createSpaceStatusUseCase;
    private final UpdateSpaceStatusUseCase updateSpaceStatusUseCase;
    private final DeleteSpaceStatusUseCase deleteSpaceStatusUseCase;
    private final GetSpaceStatusUseCase getSpaceStatusUseCase;
    private final GetAllSpaceStatusesUseCase getAllSpaceStatusesUseCase;
    private final SpaceStatusDtoMapper spaceStatusDtoMapper;

    @PostMapping(SpaceStatusEndpoint.SPACE_STATUS_CREATE_ENDPOINT)
    public ResponseEntity<SpaceStatusResponseDto> create(@Valid @RequestBody CreateSpaceStatusRequestDto request) {
        CreateSpaceStatusCommand command = spaceStatusDtoMapper.toCommand(request);
        SpaceStatus spaceStatus = createSpaceStatusUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceStatusDtoMapper.toDto(spaceStatus));
    }

    @GetMapping(SpaceStatusEndpoint.SPACE_STATUS_BASE)
    public ResponseEntity<PaginationResponseDto<SpaceStatusResponseDto>> getAll(
            @Valid SpaceStatusPaginationRequestDto requestDto,
            @Valid PaginationRequestDto paginationRequestDto
    ) {

        SpaceStatusPaginationQuery query = spaceStatusDtoMapper.toQuery(requestDto, paginationRequestDto);

        PageResult<SpaceStatus> pageResult = getAllSpaceStatusesUseCase.execute(query);

        PaginationResponseDto<SpaceStatusResponseDto> response = spaceStatusDtoMapper.toDto(pageResult);

        return ResponseEntity.ok(response);
    }

    @GetMapping(SpaceStatusEndpoint.SPACE_STATUS_GET_ALL_ENDPOIN)
    public ResponseEntity<List<SpaceStatusResponseDto>> getAll() {
        List<SpaceStatusResponseDto> spaceStatuses = getAllSpaceStatusesUseCase
                .execute()
                .stream()
                .map(spaceStatusDtoMapper::toDto).toList();

        return ResponseEntity.ok(spaceStatuses);
    }

    @GetMapping(SpaceStatusEndpoint.SPACE_STATUS_BY_ID)
    public ResponseEntity<SpaceStatusResponseDto> getById(@PathVariable Long id) {
        GetSpaceStatusQuery query = new GetSpaceStatusQuery(id);
        SpaceStatus spaceStatus = getSpaceStatusUseCase.execute(query);
        return ResponseEntity.ok(spaceStatusDtoMapper.toDto(spaceStatus));
    }

    @PutMapping(SpaceStatusEndpoint.SPACE_STATUS_UPDATE_ENDPOINT)
    public ResponseEntity<SpaceStatusResponseDto> update(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateSpaceStatusRequestDto request) {
        UpdateSpaceStatusCommand command = spaceStatusDtoMapper.toCommand(id, request);
        SpaceStatus spaceStatus = updateSpaceStatusUseCase.execute(command);
        return ResponseEntity.ok(spaceStatusDtoMapper.toDto(spaceStatus));
    }

    @DeleteMapping(SpaceStatusEndpoint.SPACE_STATUS_DELETE_ENDPOINT)
    public ResponseEntity<Void> delete(@PathVariable(name = "id")  Long id) {
        DeleteSpaceStatusCommand command = new DeleteSpaceStatusCommand(id);
        deleteSpaceStatusUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }
}
