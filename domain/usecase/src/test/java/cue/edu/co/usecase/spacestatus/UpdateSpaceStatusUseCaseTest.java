package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.commands.UpdateSpaceStatusCommand;
import cue.edu.co.model.spacestatus.exceptions.DuplicateSpaceStatusNameException;
import cue.edu.co.model.spacestatus.exceptions.SpaceStatusNotFoundException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSpaceStatusUseCaseTest {

    @Mock
    private SpaceStatusRepository spaceStatusRepository;

    @InjectMocks
    private UpdateSpaceStatusUseCase updateSpaceStatusUseCase;

    private UpdateSpaceStatusCommand command;
    private SpaceStatus existingSpaceStatus;

    @BeforeEach
    void setUp() {
        command = new UpdateSpaceStatusCommand(1L, "Available Updated", "Updated description", false);

        existingSpaceStatus = SpaceStatus.builder()
                .id(1L)
                .name("Available")
                .description("Original description")
                .canBeReserved(true)
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
    }

    @Test
    void shouldUpdateSpaceStatusSuccessfully() {
        // Arrange
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceStatus));
        when(spaceStatusRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(spaceStatusRepository.save(any(SpaceStatus.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        SpaceStatus result = updateSpaceStatusUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Available Updated", result.getName());
        assertEquals("Updated description", result.getDescription());
        assertFalse(result.getCanBeReserved());
        assertEquals(existingSpaceStatus.getCreatedAt(), result.getCreatedAt());

        verify(spaceStatusRepository, times(1)).findById(1L);
        verify(spaceStatusRepository, times(1)).existsByNameAndIdNot("Available Updated", 1L);
        verify(spaceStatusRepository, times(1)).save(any(SpaceStatus.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceStatusNotFound() {
        // Arrange
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceStatusNotFoundException.class, () -> updateSpaceStatusUseCase.execute(command));

        verify(spaceStatusRepository, times(1)).findById(1L);
        verify(spaceStatusRepository, never()).existsByNameAndIdNot(anyString(), anyLong());
        verify(spaceStatusRepository, never()).save(any(SpaceStatus.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExistsForAnotherSpaceStatus() {
        // Arrange
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceStatus));
        when(spaceStatusRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateSpaceStatusNameException.class, () -> updateSpaceStatusUseCase.execute(command));

        verify(spaceStatusRepository, times(1)).findById(1L);
        verify(spaceStatusRepository, times(1)).existsByNameAndIdNot("Available Updated", 1L);
        verify(spaceStatusRepository, never()).save(any(SpaceStatus.class));
    }
}
