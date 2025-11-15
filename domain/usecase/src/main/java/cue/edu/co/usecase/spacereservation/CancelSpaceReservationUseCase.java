package cue.edu.co.usecase.spacereservation;

import cue.edu.co.model.spacereservation.SpaceReservation;
import cue.edu.co.model.spacereservation.commands.CancelSpaceReservationCommand;
import cue.edu.co.model.spacereservation.exceptions.SpaceReservationNotFoundException;
import cue.edu.co.model.spacereservation.gateways.SpaceReservationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CancelSpaceReservationUseCase {
    private final SpaceReservationRepository spaceReservationRepository;

    public void execute(CancelSpaceReservationCommand command){

        SpaceReservation spaceReservation = spaceReservationRepository
                .findByEventId(command.eventId())
                .orElseThrow(SpaceReservationNotFoundException::new);


        spaceReservationRepository.deleteById(spaceReservation.getId());
    }
}
