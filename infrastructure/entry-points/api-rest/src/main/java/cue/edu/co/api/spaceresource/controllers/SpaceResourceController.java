package cue.edu.co.api.spaceresource.controllers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.spaceresource.constants.SpaceResourceEndpoint;
import cue.edu.co.api.spaceresource.dtos.CreateSpaceResourceRequestDto;
import cue.edu.co.api.spaceresource.dtos.SpaceResourcePaginationRequestDto;
import cue.edu.co.api.spaceresource.dtos.SpaceResourceResponseDto;
import cue.edu.co.api.spaceresource.dtos.UpdateSpaceResourceRequestDto;
import cue.edu.co.api.spaceresource.mappers.SpaceResourceDtoMapper;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.commands.CreateSpaceResourceCommand;
import cue.edu.co.model.spaceresource.commands.DeleteSpaceResourceCommand;
import cue.edu.co.model.spaceresource.commands.UpdateSpaceResourceCommand;
import cue.edu.co.model.spaceresource.queries.GetSpaceResourceQuery;
import cue.edu.co.model.spaceresource.queries.SpaceResourcePaginationQuery;
import cue.edu.co.usecase.spaceresource.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceResourceController {
    private final CreateSpaceResourceUseCase createSpaceResourceUseCase;
    private final UpdateSpaceResourceUseCase updateSpaceResourceUseCase;
    private final DeleteSpaceResourceUseCase deleteSpaceResourceUseCase;
    private final GetSpaceResourceUseCase getSpaceResourceUseCase;
    private final GetAllSpaceResourcesUseCase getAllSpaceResourcesUseCase;
    private final SpaceResourceDtoMapper spaceResourceDtoMapper;

    @PostMapping(SpaceResourceEndpoint.SPACE_RESOURCE_CREATE_ENDPOINT)
    public ResponseEntity<SpaceResourceResponseDto> create(@Valid @RequestBody CreateSpaceResourceRequestDto request) {
        CreateSpaceResourceCommand command = spaceResourceDtoMapper.toCommand(request);
        SpaceResource spaceResource = createSpaceResourceUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceResourceDtoMapper.toDto(spaceResource));
    }

    @GetMapping(SpaceResourceEndpoint.SPACE_RESOURCE_BASE)
    public ResponseEntity<PaginationResponseDto<SpaceResourceResponseDto>> getAll(
            @Valid SpaceResourcePaginationRequestDto requestDto,
            @Valid PaginationRequestDto paginationRequestDto
            ) {

        SpaceResourcePaginationQuery query = spaceResourceDtoMapper.toQuery(requestDto, paginationRequestDto);

        PageResult<SpaceResource> pageResult = getAllSpaceResourcesUseCase.execute(query);

        PaginationResponseDto<SpaceResourceResponseDto> response = spaceResourceDtoMapper.toDto(pageResult);

        return ResponseEntity.ok(response);
    }

    @GetMapping(SpaceResourceEndpoint.SPACE_RESOURCE_GET_ALL)
    public ResponseEntity<List<SpaceResourceResponseDto>> getAll() {
        List<SpaceResourceResponseDto> spaceResources = getAllSpaceResourcesUseCase
                .execute()
                .stream()
                .map(spaceResourceDtoMapper::toDto)
                .toList();

        return ResponseEntity.ok(spaceResources);
    }

    @GetMapping(SpaceResourceEndpoint.SPACE_RESOURCE_BY_ID)
    public ResponseEntity<SpaceResourceResponseDto> getById(@PathVariable Long id) {
        GetSpaceResourceQuery query = new GetSpaceResourceQuery(id);
        SpaceResource spaceResource = getSpaceResourceUseCase.execute(query);
        return ResponseEntity.ok(spaceResourceDtoMapper.toDto(spaceResource));
    }

    @PutMapping(SpaceResourceEndpoint.SPACE_RESOURCE_UPDATE_ENDPOINT)
    public ResponseEntity<SpaceResourceResponseDto> update(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateSpaceResourceRequestDto request) {
        UpdateSpaceResourceCommand command = spaceResourceDtoMapper.toCommand(id, request);
        SpaceResource spaceResource = updateSpaceResourceUseCase.execute(command);
        return ResponseEntity.ok(spaceResourceDtoMapper.toDto(spaceResource));
    }

    @DeleteMapping(SpaceResourceEndpoint.SPACE_RESOURCE_DELETE_ENDPOINT)
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        DeleteSpaceResourceCommand command = new DeleteSpaceResourceCommand(id);
        deleteSpaceResourceUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }
}
