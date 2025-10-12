package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.commands.DeleteSpaceStatusCommand;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteSpaceStatusUseCaseTest {

    @Mock
    private SpaceStatusRepository spaceStatusRepository;

    @InjectMocks
    private DeleteSpaceStatusUseCase deleteSpaceStatusUseCase;

    private DeleteSpaceStatusCommand command;
    private SpaceStatus existingSpaceStatus;

    @BeforeEach
    void setUp() {
        command = new DeleteSpaceStatusCommand(1L);

        existingSpaceStatus = SpaceStatus.builder()
                .id(1L)
                .name("Available")
                .description("Space is available")
                .canBeReserved(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldDeleteSpaceStatusSuccessfully() {
        // Arrange
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceStatus));
        doNothing().when(spaceStatusRepository).deleteById(anyLong());

        // Act
        assertDoesNotThrow(() -> deleteSpaceStatusUseCase.execute(command));

        // Assert
        verify(spaceStatusRepository, times(1)).findById(1L);
        verify(spaceStatusRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSpaceStatusNotFound() {
        // Arrange
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceStatusNotFoundException.class, () -> deleteSpaceStatusUseCase.execute(command));

        verify(spaceStatusRepository, times(1)).findById(1L);
        verify(spaceStatusRepository, never()).deleteById(anyLong());
    }
}
