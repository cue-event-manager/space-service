package cue.edu.co.usecase.spacereservation;

import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.spacereservation.commands.ValidateSpaceAvailabilityCommand;
import cue.edu.co.model.spacereservation.exceptions.InvalidReservationTimeRangeException;
import cue.edu.co.model.spacereservation.exceptions.SpaceNotReservableException;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.spacereservation.gateways.SpaceReservationRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@RequiredArgsConstructor
public class ValidateSpaceAvailabilityUseCase {

    private final SpaceRepository spaceRepository;
    private final SpaceReservationRepository spaceReservationRepository;

    public boolean execute(ValidateSpaceAvailabilityCommand command) {
        Space space = spaceRepository
                .findById(command.spaceId())
                .orElseThrow(SpaceNotFoundException::new);

        validateReservable(space);
        validateTimeRange(command.startTime(), command.endTime());


        boolean hasConflict = spaceReservationRepository.existsOverlappingReservation(
                command.spaceId(),
                command.date(),
                command.startTime(),
                command.endTime(),
                command.eventIdToExclude()
        );

        return !hasConflict;
    }

    private void validateReservable(Space space) {
        if (space.getStatus() == null || Boolean.FALSE.equals(space.getStatus().getCanBeReserved())) {
            throw new SpaceNotReservableException(space.getName());
        }
    }

    private void validateTimeRange(LocalTime start, LocalTime end) {
        if (!start.isBefore(end))
            throw new InvalidReservationTimeRangeException();
    }
}