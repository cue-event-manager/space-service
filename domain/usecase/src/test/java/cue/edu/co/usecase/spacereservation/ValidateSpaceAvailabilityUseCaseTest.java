package cue.edu.co.usecase.spacereservation;


import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.spacereservation.commands.ValidateSpaceAvailabilityCommand;
import cue.edu.co.model.spacereservation.exceptions.InvalidReservationTimeRangeException;
import cue.edu.co.model.spacereservation.exceptions.SpaceNotReservableException;
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

class ValidateSpaceAvailabilityUseCaseTest {

    @Mock
    private SpaceRepository spaceRepository;

    @Mock
    private SpaceReservationRepository spaceReservationRepository;

    @InjectMocks
    private ValidateSpaceAvailabilityUseCase useCase;

    private Space activeSpace;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        activeSpace = Space.builder()
                .id(1L)
                .name("Laboratorio 301")
                .status(SpaceStatus.builder().canBeReserved(true).build())
                .build();
    }

    @Test
    void shouldReturnTrueWhenSpaceIsAvailable() {
        // Arrange
        ValidateSpaceAvailabilityCommand cmd = new ValidateSpaceAvailabilityCommand(
                1L, LocalDate.now().plusDays(1),
                LocalTime.of(9, 0), LocalTime.of(11, 0)
        );
        when(spaceRepository.findById(1L)).thenReturn(Optional.of(activeSpace));
        when(spaceReservationRepository.existsOverlappingReservation(any(), any(), any(), any()))
                .thenReturn(false);

        // Act
        boolean result = useCase.execute(cmd);

        // Assert
        assertTrue(result);
        verify(spaceReservationRepository).existsOverlappingReservation(any(), any(), any(), any());
    }

    @Test
    void shouldReturnFalseWhenConflictExists() {
        ValidateSpaceAvailabilityCommand cmd = new ValidateSpaceAvailabilityCommand(
                1L, LocalDate.now().plusDays(1),
                LocalTime.of(9, 0), LocalTime.of(11, 0)
        );
        when(spaceRepository.findById(1L)).thenReturn(Optional.of(activeSpace));
        when(spaceReservationRepository.existsOverlappingReservation(any(), any(), any(), any()))
                .thenReturn(true);

        boolean result = useCase.execute(cmd);

        assertFalse(result);
    }

    @Test
    void shouldThrowWhenSpaceNotFound() {
        when(spaceRepository.findById(1L)).thenReturn(Optional.empty());

        ValidateSpaceAvailabilityCommand cmd = new ValidateSpaceAvailabilityCommand(
                1L, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0)
        );

        assertThrows(SpaceNotFoundException.class, () -> useCase.execute(cmd));
    }

    @Test
    void shouldThrowWhenSpaceNotReservable() {
        activeSpace.getStatus().setCanBeReserved(false);
        when(spaceRepository.findById(1L)).thenReturn(Optional.of(activeSpace));

        ValidateSpaceAvailabilityCommand cmd = new ValidateSpaceAvailabilityCommand(
                1L, LocalDate.now().plusDays(1),
                LocalTime.of(10, 0), LocalTime.of(12, 0)
        );

        assertThrows(SpaceNotReservableException.class, () -> useCase.execute(cmd));
    }

    @Test
    void shouldThrowWhenInvalidTimeRange() {
        when(spaceRepository.findById(1L)).thenReturn(Optional.of(activeSpace));

        ValidateSpaceAvailabilityCommand cmd = new ValidateSpaceAvailabilityCommand(
                1L, LocalDate.now().plusDays(1),
                LocalTime.of(12, 0), LocalTime.of(10, 0)
        );

        assertThrows(InvalidReservationTimeRangeException.class, () -> useCase.execute(cmd));
    }
}
