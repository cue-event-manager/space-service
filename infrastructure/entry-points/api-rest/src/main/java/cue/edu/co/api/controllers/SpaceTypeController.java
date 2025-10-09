package cue.edu.co.api.controllers;

import cue.edu.co.api.dto.request.CreateSpaceTypeRequestDto;
import cue.edu.co.api.dto.request.SpaceTypePageRequestDto;
import cue.edu.co.api.dto.request.UpdateSpaceTypeRequestDto;
import cue.edu.co.api.dto.response.SpaceTypePageResponseDto;
import cue.edu.co.api.dto.response.SpaceTypeResponseDto;
import cue.edu.co.api.mappers.SpaceTypeApiMapper;
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
    private final GetSpaceTypePageUseCase getSpaceTypePageUseCase;
    private final SpaceTypeApiMapper spaceTypeApiMapper;

    @PostMapping
    public ResponseEntity<SpaceTypeResponseDto> create(@Valid @RequestBody CreateSpaceTypeRequestDto request) {
        CreateSpaceTypeCommand command = spaceTypeApiMapper.toCreateCommand(request);
        SpaceType spaceType = createSpaceTypeUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceTypeApiMapper.toResponse(spaceType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceTypeResponseDto> getById(@PathVariable Long id) {
        GetSpaceTypeQuery query = spaceTypeApiMapper.toQuery(id);
        SpaceType spaceType = getSpaceTypeUseCase.execute(query);
        return ResponseEntity.ok(spaceTypeApiMapper.toResponse(spaceType));
    }

    @GetMapping
    public ResponseEntity<SpaceTypePageResponseDto> getPage(@Valid @ModelAttribute SpaceTypePageRequestDto request) {
        GetSpaceTypePageQuery query = spaceTypeApiMapper.toPageQuery(request);
        SpaceTypePageResult result = getSpaceTypePageUseCase.execute(query);
        return ResponseEntity.ok(spaceTypeApiMapper.toPageResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceTypeResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSpaceTypeRequestDto request) {
        UpdateSpaceTypeCommand command = spaceTypeApiMapper.toUpdateCommand(id, request);
        SpaceType spaceType = updateSpaceTypeUseCase.execute(command);
        return ResponseEntity.ok(spaceTypeApiMapper.toResponse(spaceType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        DeleteSpaceTypeCommand command = spaceTypeApiMapper.toDeleteCommand(id);
        deleteSpaceTypeUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }
}
