package cue.edu.co.api.spacetype.controllers;

import cue.edu.co.api.spacetype.dtos.CreateSpaceTypeRequestDto;
import cue.edu.co.api.spacetype.dtos.SpaceTypeResponseDto;
import cue.edu.co.api.spacetype.dtos.UpdateSpaceTypeRequestDto;
import cue.edu.co.api.spacetype.mappers.SpaceTypeDtoMapper;
import cue.edu.co.model.spacetype.*;
import cue.edu.co.usecase.spacetype.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/space-types")
@RequiredArgsConstructor
public class SpaceTypeController {
    private final CreateSpaceTypeUseCase createSpaceTypeUseCase;
    private final UpdateSpaceTypeUseCase updateSpaceTypeUseCase;
    private final DeleteSpaceTypeUseCase deleteSpaceTypeUseCase;
    private final GetSpaceTypeUseCase getSpaceTypeUseCase;
    private final SpaceTypeDtoMapper spaceTypeDtoMapper;

    @PostMapping
    public ResponseEntity<SpaceTypeResponseDto> create(@Valid @RequestBody CreateSpaceTypeRequestDto request) {
        CreateSpaceTypeCommand command = spaceTypeDtoMapper.toCommand(request);
        SpaceType spaceType = createSpaceTypeUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceTypeDtoMapper.toDto(spaceType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceTypeResponseDto> getById(@PathVariable Long id) {
        GetSpaceTypeQuery query = new GetSpaceTypeQuery(id);
        SpaceType spaceType = getSpaceTypeUseCase.execute(query);
        return ResponseEntity.ok(spaceTypeDtoMapper.toDto(spaceType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceTypeResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSpaceTypeRequestDto request) {
        UpdateSpaceTypeCommand command = spaceTypeDtoMapper.toCommand(id, request);
        SpaceType spaceType = updateSpaceTypeUseCase.execute(command);
        return ResponseEntity.ok(spaceTypeDtoMapper.toDto(spaceType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        DeleteSpaceTypeCommand command = new DeleteSpaceTypeCommand(id);
        deleteSpaceTypeUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }
}
