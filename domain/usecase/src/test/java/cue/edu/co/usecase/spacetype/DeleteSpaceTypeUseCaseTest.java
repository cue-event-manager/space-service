package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.commands.DeleteSpaceTypeCommand;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.exceptions.SpaceTypeNotFoundException;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
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
class DeleteSpaceTypeUseCaseTest {

    @Mock
    private SpaceTypeRepository spaceTypeRepository;

    @InjectMocks
    private DeleteSpaceTypeUseCase deleteSpaceTypeUseCase;

    private DeleteSpaceTypeCommand command;
    private SpaceType existingSpaceType;

    @BeforeEach
    void setUp() {
        command = new DeleteSpaceTypeCommand(1L);

        existingSpaceType = SpaceType.builder()
                .id(1L)
                .name("Auditorium")
                .description("Large auditorium")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldDeleteSpaceTypeSuccessfully() {
        // Arrange
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceType));
        doNothing().when(spaceTypeRepository).deleteById(anyLong());

        // Act
        assertDoesNotThrow(() -> deleteSpaceTypeUseCase.execute(command));

        // Assert
        verify(spaceTypeRepository, times(1)).findById(1L);
        verify(spaceTypeRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSpaceTypeNotFound() {
        // Arrange
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceTypeNotFoundException.class, () -> deleteSpaceTypeUseCase.execute(command));

        verify(spaceTypeRepository, times(1)).findById(1L);
        verify(spaceTypeRepository, never()).deleteById(anyLong());
    }
}
