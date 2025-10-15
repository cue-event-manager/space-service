package cue.edu.co.config;

import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.usecase.campus.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CampusUseCaseConfig {

    @Bean
    public CreateCampusUseCase createCampusUseCase(CampusRepository campusRepository) {
        return new CreateCampusUseCase(campusRepository);
    }

    @Bean
    public UpdateCampusUseCase updateCampusUseCase(CampusRepository campusRepository) {
        return new UpdateCampusUseCase(campusRepository);
    }

    @Bean
    public DeleteCampusUseCase deleteCampusUseCase(CampusRepository campusRepository) {
        return new DeleteCampusUseCase(campusRepository);
    }

    @Bean
    public GetCampusUseCase getCampusUseCase(CampusRepository campusRepository) {
        return new GetCampusUseCase(campusRepository);
    }

    @Bean
    public GetAllCampusesUseCase getAllCampusesUseCase(CampusRepository campusRepository) {
        return new GetAllCampusesUseCase(campusRepository);
    }
}
