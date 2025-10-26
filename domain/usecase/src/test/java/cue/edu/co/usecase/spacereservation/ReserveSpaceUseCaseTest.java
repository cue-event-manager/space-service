package cue.edu.co.usecase.spacereservation;


import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.spacereservation.SpaceReservation;
import cue.edu.co.model.spacereservation.commands.ReserveSpaceCommand;
import cue.edu.co.model.spacereservation.exceptions.*;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.spacereservation.gateways.SpaceReservationRepository;
import cue.edu.co.model.spacestatus.SpaceStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReserveSpaceUseCaseTest {

    @Mock
    private SpaceRepository spaceRepository;

    @Mock
    private SpaceReservationRepository spaceReservationRepository;

    @InjectMocks
    private ReserveSpaceUseCase reserveSpaceUseCase;

    private Space activeSpace;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        activeSpace = Space.builder()
                .id(1L)
                .name("Sala 101")
                .status(SpaceStatus.builder().canBeReserved(true).build())
                .build();
    }

    private ReserveSpaceCommand buildCommand() {
        return new ReserveSpaceCommand(
                1L,
                10L,
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0)
        );
    }

    @Test
    void shouldReserveSpaceSuccessfully() {
        ReserveSpaceCommand command = buildCommand();
        SpaceReservation expectedReservation = SpaceReservation.builder().id(99L).build();

        when(spaceRepository.findById(command.spaceId())).thenReturn(Optional.of(activeSpace));
        when(spaceReservationRepository.existsOverlappingReservation(any(), any(), any(), any())).thenReturn(false);
        when(spaceReservationRepository.save(any())).thenReturn(expectedReservation);

        SpaceReservation result = reserveSpaceUseCase.execute(command);

        assertEquals(expectedReservation, result);
        verify(spaceReservationRepository).save(any());
    }

    @Test
    void shouldThrowWhenSpaceNotFound() {
        ReserveSpaceCommand command = buildCommand();

        when(spaceRepository.findById(command.spaceId())).thenReturn(Optional.empty());

        assertThrows(SpaceNotFoundException.class, () -> reserveSpaceUseCase.execute(command));
    }

    @Test
    void shouldThrowWhenSpaceIsNotReservable() {
        ReserveSpaceCommand command = buildCommand();
        activeSpace.getStatus().setCanBeReserved(false);

        when(spaceRepository.findById(command.spaceId())).thenReturn(Optional.of(activeSpace));

        assertThrows(SpaceNotReservableException.class, () -> reserveSpaceUseCase.execute(command));
    }

    @Test
    void shouldThrowWhenDateIsInPast() {
        ReserveSpaceCommand command = new ReserveSpaceCommand(
                1L, 10L, LocalDate.now().minusDays(1),
                LocalTime.of(10, 0), LocalTime.of(12, 0)
        );

        when(spaceRepository.findById(command.spaceId())).thenReturn(Optional.of(activeSpace));

        assertThrows(InvalidReservationDateException.class, () -> reserveSpaceUseCase.execute(command));
    }

    @Test
    void shouldThrowWhenTimeRangeInvalid() {
        ReserveSpaceCommand command = new ReserveSpaceCommand(
                1L, 10L, LocalDate.now().plusDays(1),
                LocalTime.of(12, 0), LocalTime.of(10, 0)
        );

        when(spaceRepository.findById(command.spaceId())).thenReturn(Optional.of(activeSpace));

        assertThrows(InvalidReservationTimeRangeException.class, () -> reserveSpaceUseCase.execute(command));
    }

    @Test
    void shouldThrowWhenSpaceAlreadyReserved() {
        ReserveSpaceCommand command = buildCommand();

        when(spaceRepository.findById(command.spaceId())).thenReturn(Optional.of(activeSpace));
        when(spaceReservationRepository.existsOverlappingReservation(any(), any(), any(), any())).thenReturn(true);

        assertThrows(SpaceAlreadyReservedException.class, () -> reserveSpaceUseCase.execute(command));
    }
}