package cue.edu.co.usecase.spacereservation;

import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.spacereservation.SpaceReservation;
import cue.edu.co.model.spacereservation.commands.ReserveSpaceCommand;
import cue.edu.co.model.spacereservation.exceptions.SpaceAlreadyReservedException;
import cue.edu.co.model.spacereservation.exceptions.SpaceNotReservableException;
import cue.edu.co.model.spacereservation.gateways.SpaceReservationRepository;
import lombok.RequiredArgsConstructor;

import cue.edu.co.model.spacereservation.exceptions.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
public class ReserveSpaceUseCase {

    private final SpaceReservationRepository spaceReservationRepository;
    private final SpaceRepository spaceRepository;

    public SpaceReservation execute(ReserveSpaceCommand command) {
        Space space = spaceRepository
                .findById(command.spaceId())
                .orElseThrow(SpaceNotFoundException::new);

        validateReservationDate(command.date());
        validateTimeRange(command.startTime(), command.endTime());
        validateSpaceState(space);
        validateSpaceAvailability(command);

        return spaceReservationRepository.save(command.toDomain());
    }

    private void validateSpaceState(Space space) {
        if (space.getStatus() == null || Boolean.FALSE.equals(space.getStatus().getCanBeReserved())) {
            throw new SpaceNotReservableException(space.getName());
        }
    }

    private void validateReservationDate(LocalDate date) {
        if (date.isBefore(LocalDate.now()))
            throw new InvalidReservationDateException();
    }

    private void validateTimeRange(LocalTime start, LocalTime end) {

        if (!start.isBefore(end))
            throw new InvalidReservationTimeRangeException();
    }

    private void validateSpaceAvailability(ReserveSpaceCommand command) {
        boolean overlap = spaceReservationRepository.existsOverlappingReservation(
                command.spaceId(),
                command.date(),
                command.startTime(),
                command.endTime(),
                null
        );

        if (overlap)
            throw new SpaceAlreadyReservedException(command.date(), command.startTime(), command.endTime());
    }
}