package cue.edu.co.config;

import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.usecase.spaceresource.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpaceResourceUseCaseConfig {

    @Bean
    public CreateSpaceResourceUseCase createSpaceResourceUseCase(SpaceResourceRepository spaceResourceRepository) {
        return new CreateSpaceResourceUseCase(spaceResourceRepository);
    }

    @Bean
    public UpdateSpaceResourceUseCase updateSpaceResourceUseCase(SpaceResourceRepository spaceResourceRepository) {
        return new UpdateSpaceResourceUseCase(spaceResourceRepository);
    }

    @Bean
    public DeleteSpaceResourceUseCase deleteSpaceResourceUseCase(SpaceResourceRepository spaceResourceRepository) {
        return new DeleteSpaceResourceUseCase(spaceResourceRepository);
    }

    @Bean
    public GetSpaceResourceUseCase getSpaceResourceUseCase(SpaceResourceRepository spaceResourceRepository) {
        return new GetSpaceResourceUseCase(spaceResourceRepository);
    }

    @Bean
    public GetAllSpaceResourcesUseCase getAllSpaceResourcesUseCase(SpaceResourceRepository spaceResourceRepository) {
        return new GetAllSpaceResourcesUseCase(spaceResourceRepository);
    }

}
