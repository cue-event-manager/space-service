package cue.edu.co.api.space.controllers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.space.constants.SpaceEndpoint;
import cue.edu.co.api.space.dtos.CreateSpaceRequestDto;
import cue.edu.co.api.space.dtos.SpacePaginationRequestDto;
import cue.edu.co.api.space.dtos.SpaceResponseDto;
import cue.edu.co.api.space.dtos.UpdateSpaceRequestDto;
import cue.edu.co.api.space.mappers.SpaceDtoMapper;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.space.queries.GetSpaceQuery;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.commands.CreateSpaceCommand;
import cue.edu.co.model.space.commands.DeleteSpaceCommand;
import cue.edu.co.model.space.commands.UpdateSpaceCommand;
import cue.edu.co.model.space.queries.SpacePaginationQuery;
import cue.edu.co.usecase.space.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SpaceController {
    private final CreateSpaceUseCase createSpaceUseCase;
    private final UpdateSpaceUseCase updateSpaceUseCase;
    private final DeleteSpaceUseCase deleteSpaceUseCase;
    private final GetSpaceUseCase getSpaceUseCase;
    private final GetAllSpacesUseCase getAllSpacesUseCase;
    private final SpaceDtoMapper spaceDtoMapper;

    @PostMapping(SpaceEndpoint.SPACE_CREATE_ENDPOINT)
    public ResponseEntity<SpaceResponseDto> create(@Valid @RequestBody CreateSpaceRequestDto request) {
        CreateSpaceCommand command = spaceDtoMapper.toCommand(request);
        Space space = createSpaceUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceDtoMapper.toDto(space));
    }

    @GetMapping(SpaceEndpoint.SPACE_BASE)
    public ResponseEntity<PaginationResponseDto<SpaceResponseDto>> getAll(
            @Valid SpacePaginationRequestDto requestDto,
            @Valid PaginationRequestDto paginationRequestDto
    ) {
        SpacePaginationQuery query = spaceDtoMapper.toQuery(requestDto, paginationRequestDto);

        PageResult<Space> pageResult = getAllSpacesUseCase.execute(query);

        PaginationResponseDto<SpaceResponseDto> response = spaceDtoMapper.toDto(pageResult);

        return ResponseEntity.ok(response);
    }

    @GetMapping(SpaceEndpoint.SPACE_BY_ID)
    public ResponseEntity<SpaceResponseDto> getById(@PathVariable Long id) {
        GetSpaceQuery query = new GetSpaceQuery(id);
        Space space = getSpaceUseCase.execute(query);
        return ResponseEntity.ok(spaceDtoMapper.toDto(space));
    }

    @PutMapping(SpaceEndpoint.SPACE_UPDATE_ENDPOINT)
    public ResponseEntity<SpaceResponseDto> update(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateSpaceRequestDto request) {
        UpdateSpaceCommand command = spaceDtoMapper.toCommand(id, request);
        Space space = updateSpaceUseCase.execute(command);
        return ResponseEntity.ok(spaceDtoMapper.toDto(space));
    }

    @DeleteMapping(SpaceEndpoint.SPACE_DELETE_ENDPOINT)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        DeleteSpaceCommand command = new DeleteSpaceCommand(id);
        deleteSpaceUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }
}
