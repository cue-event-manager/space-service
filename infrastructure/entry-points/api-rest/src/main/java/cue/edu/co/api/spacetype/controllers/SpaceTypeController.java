package cue.edu.co.api.spacetype.controllers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.spacetype.constants.SpaceTypeEndpoint;
import cue.edu.co.api.spacetype.dtos.CreateSpaceTypeRequestDto;
import cue.edu.co.api.spacetype.dtos.SpaceTypePaginationRequestDto;
import cue.edu.co.api.spacetype.dtos.SpaceTypeResponseDto;
import cue.edu.co.api.spacetype.dtos.UpdateSpaceTypeRequestDto;
import cue.edu.co.api.spacetype.mappers.SpaceTypeDtoMapper;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.commands.CreateSpaceTypeCommand;
import cue.edu.co.model.spacetype.commands.DeleteSpaceTypeCommand;
import cue.edu.co.model.spacetype.commands.UpdateSpaceTypeCommand;
import cue.edu.co.model.spacetype.GetSpaceTypeQuery;
import cue.edu.co.model.spacetype.queries.SpaceTypePaginationQuery;
import cue.edu.co.usecase.spacetype.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SpaceTypeController {
    private final CreateSpaceTypeUseCase createSpaceTypeUseCase;
    private final UpdateSpaceTypeUseCase updateSpaceTypeUseCase;
    private final DeleteSpaceTypeUseCase deleteSpaceTypeUseCase;
    private final GetSpaceTypeUseCase getSpaceTypeUseCase;
    private final GetAllSpaceTypesUseCase getAllSpaceTypesUseCase;
    private final SpaceTypeDtoMapper spaceTypeDtoMapper;

    @PostMapping(SpaceTypeEndpoint.SPACE_TYPE_CREATE_ENDPOINT)
    public ResponseEntity<SpaceTypeResponseDto> create(@Valid @RequestBody CreateSpaceTypeRequestDto request) {
        CreateSpaceTypeCommand command = spaceTypeDtoMapper.toCommand(request);
        SpaceType spaceType = createSpaceTypeUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceTypeDtoMapper.toDto(spaceType));
    }

    @GetMapping(SpaceTypeEndpoint.SPACE_TYPE_BASE)
    public ResponseEntity<PaginationResponseDto<SpaceTypeResponseDto>> getAll(
            @Valid SpaceTypePaginationRequestDto requestDto,
            @Valid PaginationRequestDto paginationRequestDto
            ) {

        SpaceTypePaginationQuery query = spaceTypeDtoMapper.toQuery(requestDto, paginationRequestDto);

        PageResult<SpaceType> pageResult = getAllSpaceTypesUseCase.execute(query);

        PaginationResponseDto<SpaceTypeResponseDto> response = spaceTypeDtoMapper.toDto(pageResult);

        return ResponseEntity.ok(response);
    }

    @GetMapping(SpaceTypeEndpoint.SPACE_TYPE_BY_ID)
    public ResponseEntity<SpaceTypeResponseDto> getById(@PathVariable Long id) {
        GetSpaceTypeQuery query = new GetSpaceTypeQuery(id);
        SpaceType spaceType = getSpaceTypeUseCase.execute(query);
        return ResponseEntity.ok(spaceTypeDtoMapper.toDto(spaceType));
    }

    @PutMapping(SpaceTypeEndpoint.SPACE_TYPE_UPDATE_ENDPOINT)
    public ResponseEntity<SpaceTypeResponseDto> update(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateSpaceTypeRequestDto request) {
        UpdateSpaceTypeCommand command = spaceTypeDtoMapper.toCommand(id, request);
        SpaceType spaceType = updateSpaceTypeUseCase.execute(command);
        return ResponseEntity.ok(spaceTypeDtoMapper.toDto(spaceType));
    }

    @DeleteMapping(SpaceTypeEndpoint.SPACE_TYPE_DELETE_ENDPOINT)
    public ResponseEntity<Void> delete(@PathVariable(name = "id")  Long id) {
        DeleteSpaceTypeCommand command = new DeleteSpaceTypeCommand(id);
        deleteSpaceTypeUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }
}

