package cue.edu.co.usecase.space;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.exceptions.CampusNotFoundException;
import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.commands.UpdateSpaceCommand;
import cue.edu.co.model.space.exceptions.DuplicateSpaceNameException;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.exceptions.SpaceResourceNotFoundException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.exceptions.SpaceStatusNotFoundException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.exceptions.SpaceTypeNotFoundException;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class UpdateSpaceUseCase {
    private final SpaceRepository spaceRepository;
    private final CampusRepository campusRepository;
    private final SpaceTypeRepository spaceTypeRepository;
    private final SpaceStatusRepository spaceStatusRepository;
    private final SpaceResourceRepository spaceResourceRepository;

    public Space execute(UpdateSpaceCommand command) {
        Space existingSpace = findSpaceById(command.id());

        validateUniqueName(command.name(), command.campusId(), command.id());

        Campus campus = findCampusById(command.campusId());
        SpaceType type = findSpaceTypeById(command.typeId());
        SpaceStatus status = findSpaceStatusById(command.statusId());
        Set<SpaceResource> resources = findResourcesByIds(command.resourceIds());

        Space space = command.toDomain(existingSpace);
        space.setCampus(campus);
        space.setType(type);
        space.setStatus(status);
        space.setResources(resources);

        return spaceRepository.save(space);
    }

    private Space findSpaceById(Long id) {
        return spaceRepository.findById(id)
                .orElseThrow(() -> new SpaceNotFoundException(id));
    }

    private void validateUniqueName(String name, Long campusId, Long id) {
        if (spaceRepository.existsByNameAndCampusIdAndIdNot(name, campusId, id)) {
            throw new DuplicateSpaceNameException(name);
        }
    }

    private Campus findCampusById(Long campusId) {
        return campusRepository.findById(campusId)
                .orElseThrow(() -> new CampusNotFoundException(campusId));
    }

    private SpaceType findSpaceTypeById(Long typeId) {
        return spaceTypeRepository.findById(typeId)
                .orElseThrow(() -> new SpaceTypeNotFoundException(typeId));
    }

    private SpaceStatus findSpaceStatusById(Long statusId) {
        return spaceStatusRepository.findById(statusId)
                .orElseThrow(() -> new SpaceStatusNotFoundException(statusId));
    }

    private Set<SpaceResource> findResourcesByIds(Set<Long> resourceIds) {
        if (resourceIds == null || resourceIds.isEmpty()) {
            return new HashSet<>();
        }

        List<SpaceResource> foundResources = resourceIds.stream()
                .map(id -> spaceResourceRepository.findById(id)
                        .orElseThrow(() -> new SpaceResourceNotFoundException(id)))
                .toList();

        return new HashSet<>(foundResources);
    }
}
