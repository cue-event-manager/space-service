package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.spaceresource.DeleteSpaceResourceCommand;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.exceptions.SpaceResourceNotFoundException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
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
class DeleteSpaceResourceUseCaseTest {

    @Mock
    private SpaceResourceRepository spaceResourceRepository;

    @InjectMocks
    private DeleteSpaceResourceUseCase deleteSpaceResourceUseCase;

    private DeleteSpaceResourceCommand command;
    private SpaceResource existingSpaceResource;

    @BeforeEach
    void setUp() {
        command = new DeleteSpaceResourceCommand(1L);

        existingSpaceResource = SpaceResource.builder()
                .id(1L)
                .name("Microphone")
                .description("Wireless microphone")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldDeleteSpaceResourceSuccessfully() {
        // Arrange
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceResource));
        doNothing().when(spaceResourceRepository).deleteById(anyLong());

        // Act
        assertDoesNotThrow(() -> deleteSpaceResourceUseCase.execute(command));

        // Assert
        verify(spaceResourceRepository, times(1)).findById(1L);
        verify(spaceResourceRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSpaceResourceNotFound() {
        // Arrange
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceResourceNotFoundException.class, () -> deleteSpaceResourceUseCase.execute(command));

        verify(spaceResourceRepository, times(1)).findById(1L);
        verify(spaceResourceRepository, never()).deleteById(anyLong());
    }
}
