package cue.edu.co.api.mappers;

import cue.edu.co.api.dto.request.CreateSpaceTypeRequestDto;
import cue.edu.co.api.dto.request.SpaceTypePageRequestDto;
import cue.edu.co.api.dto.request.UpdateSpaceTypeRequestDto;
import cue.edu.co.api.dto.response.SpaceTypePageResponseDto;
import cue.edu.co.api.dto.response.SpaceTypeResponseDto;
import cue.edu.co.model.spacetype.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SpaceTypeApiMapper {

    public CreateSpaceTypeCommand toCreateCommand(CreateSpaceTypeRequestDto dto) {
        return CreateSpaceTypeCommand.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public UpdateSpaceTypeCommand toUpdateCommand(Long id, UpdateSpaceTypeRequestDto dto) {
        return UpdateSpaceTypeCommand.builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public GetSpaceTypePageQuery toPageQuery(SpaceTypePageRequestDto dto) {
        return GetSpaceTypePageQuery.builder()
                .page(dto.getPage())
                .size(dto.getSize())
                .name(dto.getName())
                .build();
    }

    public GetSpaceTypeQuery toQuery(Long id) {
        return GetSpaceTypeQuery.builder()
                .id(id)
                .build();
    }

    public DeleteSpaceTypeCommand toDeleteCommand(Long id) {
        return DeleteSpaceTypeCommand.builder()
                .id(id)
                .build();
    }

    public SpaceTypeResponseDto toResponse(SpaceType spaceType) {
        return SpaceTypeResponseDto.builder()
                .id(spaceType.getId())
                .name(spaceType.getName())
                .description(spaceType.getDescription())
                .createdAt(spaceType.getCreatedAt())
                .build();
    }

    public SpaceTypePageResponseDto toPageResponse(SpaceTypePageResult result) {
        return SpaceTypePageResponseDto.builder()
                .content(result.getContent().stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList()))
                .page(result.getPage())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }
}
