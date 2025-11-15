package cue.edu.co.config;

import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.spacereservation.gateways.SpaceReservationRepository;
import cue.edu.co.usecase.spacereservation.CancelSpaceReservationUseCase;
import cue.edu.co.usecase.spacereservation.ReserveSpaceUseCase;
import cue.edu.co.usecase.spacereservation.ValidateSpaceAvailabilityUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpaceReservationUseCaseConfig {

    @Bean
    public ReserveSpaceUseCase reserveSpaceUseCase(SpaceReservationRepository spaceReservationRepository,
                                                   SpaceRepository spaceRepository){
        return new ReserveSpaceUseCase(spaceReservationRepository,spaceRepository);
    }

    @Bean
    public ValidateSpaceAvailabilityUseCase validateSpaceAvailabilityUseCase(SpaceReservationRepository spaceReservationRepository,
                                                                             SpaceRepository spaceRepository){
        return new ValidateSpaceAvailabilityUseCase(spaceRepository, spaceReservationRepository);
    }

    @Bean
    public CancelSpaceReservationUseCase cancelSpaceReservationUseCase(SpaceReservationRepository spaceReservationRepository){
        return new CancelSpaceReservationUseCase(spaceReservationRepository);
    }
}
