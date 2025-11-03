package cue.edu.co.api.space.controllers;

import cue.edu.co.api.common.dtos.PaginationRequestDto;
import cue.edu.co.api.common.dtos.PaginationResponseDto;
import cue.edu.co.api.space.constants.SpaceEndpoint;
import cue.edu.co.api.space.dtos.*;
import cue.edu.co.api.space.mappers.SpaceDtoMapper;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.space.queries.GetAllSpacesQuery;
import cue.edu.co.model.space.queries.GetSpaceQuery;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.commands.CreateSpaceCommand;
import cue.edu.co.model.space.commands.DeleteSpaceCommand;
import cue.edu.co.model.space.commands.UpdateSpaceCommand;
import cue.edu.co.model.space.queries.SpacePaginationQuery;
import cue.edu.co.model.spacereservation.SpaceReservation;
import cue.edu.co.model.spacereservation.commands.ReserveSpaceCommand;
import cue.edu.co.model.spacereservation.commands.ValidateSpaceAvailabilityCommand;
import cue.edu.co.usecase.space.*;
import cue.edu.co.usecase.spacereservation.ReserveSpaceUseCase;
import cue.edu.co.usecase.spacereservation.ValidateSpaceAvailabilityUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceController {
    private final CreateSpaceUseCase createSpaceUseCase;
    private final UpdateSpaceUseCase updateSpaceUseCase;
    private final DeleteSpaceUseCase deleteSpaceUseCase;
    private final GetSpaceUseCase getSpaceUseCase;
    private final GetAllSpacesUseCase getAllSpacesUseCase;
    private final ValidateSpaceAvailabilityUseCase validateSpaceAvailabilityUseCase;
    private final ReserveSpaceUseCase reserveSpaceUseCase;
    private final GetAvailableSpacesUseCase getAvailableSpacesUseCase;

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

    @GetMapping(SpaceEndpoint.SPACE_GET_ALL)
    public ResponseEntity<List<SpaceResponseDto>> getAll(
            @Valid SpacePaginationRequestDto requestDto
    ) {
        GetAllSpacesQuery getAllSpacesQuery = spaceDtoMapper.toQuery(requestDto);

        List<SpaceResponseDto> spaces = getAllSpacesUseCase
                .execute(getAllSpacesQuery)
                .stream()
                .map(spaceDtoMapper::toDto)
                .toList();

        return ResponseEntity.ok(spaces);
    }

    @GetMapping(SpaceEndpoint.SPACE_AVAILABLE)
    public  ResponseEntity<List<SpaceResponseDto>> getAvailableSpaces(
            @Valid GetAvailableSpacesRequestDto availableSpacesRequestDto
    ){
        List<Space> spaces = getAvailableSpacesUseCase.execute(spaceDtoMapper.toQuery(availableSpacesRequestDto));

        return ResponseEntity.ok(
                spaces.stream().map(spaceDtoMapper::toDto).toList()
        );
    }

    @GetMapping(SpaceEndpoint.SPACE_BY_ID)
    public ResponseEntity<SpaceResponseDto> getById(@PathVariable(name = "id")  Long id) {
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

    @GetMapping(SpaceEndpoint.SPACE_AVAILABILITY)
    public ResponseEntity<ValidateSpaceAvailabilityResponseDto> validateAvailability(
            @PathVariable("id") Long spaceId,
            @Valid ValidateSpaceAvailabilityRequestDto request
    ) {
        ValidateSpaceAvailabilityCommand command = spaceDtoMapper.toCommand(spaceId, request);
        boolean available = validateSpaceAvailabilityUseCase.execute(command);
        return ResponseEntity.ok(new ValidateSpaceAvailabilityResponseDto(available));
    }

    @PostMapping(SpaceEndpoint.SPACE_RESERVE)
    public ResponseEntity<ReserveSpaceResponseDto> reserveSpace(
            @PathVariable("id")  Long spaceId,
            @Valid @RequestBody ReserveSpaceRequestDto request
    ) {
        ReserveSpaceCommand command = spaceDtoMapper.toCommand(spaceId, request);
        SpaceReservation reservation = reserveSpaceUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceDtoMapper.toDto(reservation));
    }
}
