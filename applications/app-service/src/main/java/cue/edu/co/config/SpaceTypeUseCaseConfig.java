package cue.edu.co.config;

import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import cue.edu.co.usecase.spacetype.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpaceTypeUseCaseConfig {

    @Bean
    public CreateSpaceTypeUseCase createSpaceTypeUseCase(SpaceTypeRepository spaceTypeRepository) {
        return new CreateSpaceTypeUseCase(spaceTypeRepository);
    }

    @Bean
    public UpdateSpaceTypeUseCase updateSpaceTypeUseCase(SpaceTypeRepository spaceTypeRepository) {
        return new UpdateSpaceTypeUseCase(spaceTypeRepository);
    }

    @Bean
    public DeleteSpaceTypeUseCase deleteSpaceTypeUseCase(SpaceTypeRepository spaceTypeRepository) {
        return new DeleteSpaceTypeUseCase(spaceTypeRepository);
    }

    @Bean
    public GetSpaceTypeUseCase getSpaceTypeUseCase(SpaceTypeRepository spaceTypeRepository) {
        return new GetSpaceTypeUseCase(spaceTypeRepository);
    }

    @Bean
    public GetAllSpaceTypesUseCase getAllSpaceTypesUseCase(SpaceTypeRepository spaceTypeRepository) {
        return new GetAllSpaceTypesUseCase(spaceTypeRepository);
    }

}
