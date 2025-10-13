package cue.edu.co.config;

import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import cue.edu.co.usecase.spacestatus.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpaceStatusUseCaseConfig {

    @Bean
    public CreateSpaceStatusUseCase createSpaceStatusUseCase(SpaceStatusRepository spaceStatusRepository) {
        return new CreateSpaceStatusUseCase(spaceStatusRepository);
    }

    @Bean
    public UpdateSpaceStatusUseCase updateSpaceStatusUseCase(SpaceStatusRepository spaceStatusRepository) {
        return new UpdateSpaceStatusUseCase(spaceStatusRepository);
    }

    @Bean
    public DeleteSpaceStatusUseCase deleteSpaceStatusUseCase(SpaceStatusRepository spaceStatusRepository) {
        return new DeleteSpaceStatusUseCase(spaceStatusRepository);
    }

    @Bean
    public GetSpaceStatusUseCase getSpaceStatusUseCase(SpaceStatusRepository spaceStatusRepository) {
        return new GetSpaceStatusUseCase(spaceStatusRepository);
    }

    @Bean
    public GetAllSpaceStatusesUseCase getAllSpaceStatusesUseCase(SpaceStatusRepository spaceStatusRepository) {
        return new GetAllSpaceStatusesUseCase(spaceStatusRepository);
    }
}
