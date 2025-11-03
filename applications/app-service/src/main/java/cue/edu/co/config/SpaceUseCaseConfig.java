package cue.edu.co.config;

import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import cue.edu.co.usecase.space.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpaceUseCaseConfig {

    @Bean
    public CreateSpaceUseCase createSpaceUseCase(
            SpaceRepository spaceRepository,
            CampusRepository campusRepository,
            SpaceTypeRepository spaceTypeRepository,
            SpaceStatusRepository spaceStatusRepository,
            SpaceResourceRepository spaceResourceRepository) {
        return new CreateSpaceUseCase(
                spaceRepository,
                campusRepository,
                spaceTypeRepository,
                spaceStatusRepository,
                spaceResourceRepository
        );
    }

    @Bean
    public UpdateSpaceUseCase updateSpaceUseCase(
            SpaceRepository spaceRepository,
            CampusRepository campusRepository,
            SpaceTypeRepository spaceTypeRepository,
            SpaceStatusRepository spaceStatusRepository,
            SpaceResourceRepository spaceResourceRepository) {
        return new UpdateSpaceUseCase(
                spaceRepository,
                campusRepository,
                spaceTypeRepository,
                spaceStatusRepository,
                spaceResourceRepository
        );
    }

    @Bean
    public DeleteSpaceUseCase deleteSpaceUseCase(SpaceRepository spaceRepository) {
        return new DeleteSpaceUseCase(spaceRepository);
    }

    @Bean
    public GetSpaceUseCase getSpaceUseCase(SpaceRepository spaceRepository) {
        return new GetSpaceUseCase(spaceRepository);
    }

    @Bean
    public GetAllSpacesUseCase getAllSpacesUseCase(SpaceRepository spaceRepository) {
        return new GetAllSpacesUseCase(spaceRepository);
    }

    @Bean
    public GetAvailableSpacesUseCase getAvailableSpacesUseCase(SpaceRepository spaceRepository){
        return new GetAvailableSpacesUseCase(spaceRepository);
    }
}
