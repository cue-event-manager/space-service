package cue.edu.co.usecase.space;

import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.commands.DeleteSpaceCommand;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.space.gateways.SpaceRepository;
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
class DeleteSpaceUseCaseTest {

    @Mock
    private SpaceRepository spaceRepository;

    @InjectMocks
    private DeleteSpaceUseCase deleteSpaceUseCase;

    private DeleteSpaceCommand command;
    private Space existingSpace;

    @BeforeEach
    void setUp() {
        command = new DeleteSpaceCommand(1L);

        existingSpace = Space.builder()
                .id(1L)
                .name("Auditorium A")
                .capacity(200)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldDeleteSpaceSuccessfully() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpace));
        doNothing().when(spaceRepository).deleteById(anyLong());

        // Act
        deleteSpaceUseCase.execute(command);

        // Assert
        verify(spaceRepository, times(1)).findById(1L);
        verify(spaceRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSpaceNotFound() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceNotFoundException exception = assertThrows(
                SpaceNotFoundException.class,
                () -> deleteSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).deleteById(anyLong());
    }
}
