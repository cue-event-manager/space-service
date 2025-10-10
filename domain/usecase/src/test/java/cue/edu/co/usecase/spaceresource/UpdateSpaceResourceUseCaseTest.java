package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.UpdateSpaceResourceCommand;
import cue.edu.co.model.spaceresource.exceptions.DuplicateSpaceResourceNameException;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSpaceResourceUseCaseTest {

    @Mock
    private SpaceResourceRepository spaceResourceRepository;

    @InjectMocks
    private UpdateSpaceResourceUseCase updateSpaceResourceUseCase;

    private UpdateSpaceResourceCommand command;
    private SpaceResource existingSpaceResource;

    @BeforeEach
    void setUp() {
        command = new UpdateSpaceResourceCommand(1L, "Whiteboard Updated", "Updated description");

        existingSpaceResource = SpaceResource.builder()
                .id(1L)
                .name("Whiteboard")
                .description("Original description")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
    }

    @Test
    void shouldUpdateSpaceResourceSuccessfully() {
        // Arrange
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceResource));
        when(spaceResourceRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(spaceResourceRepository.save(any(SpaceResource.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        SpaceResource result = updateSpaceResourceUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Whiteboard Updated", result.getName());
        assertEquals("Updated description", result.getDescription());
        assertEquals(existingSpaceResource.getCreatedAt(), result.getCreatedAt());

        verify(spaceResourceRepository, times(1)).findById(1L);
        verify(spaceResourceRepository, times(1)).existsByNameAndIdNot("Whiteboard Updated", 1L);
        verify(spaceResourceRepository, times(1)).save(any(SpaceResource.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceResourceNotFound() {
        // Arrange
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceResourceNotFoundException.class, () -> updateSpaceResourceUseCase.execute(command));

        verify(spaceResourceRepository, times(1)).findById(1L);
        verify(spaceResourceRepository, never()).existsByNameAndIdNot(anyString(), anyLong());
        verify(spaceResourceRepository, never()).save(any(SpaceResource.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExistsForAnotherSpaceResource() {
        // Arrange
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceResource));
        when(spaceResourceRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateSpaceResourceNameException.class, () -> updateSpaceResourceUseCase.execute(command));

        verify(spaceResourceRepository, times(1)).findById(1L);
        verify(spaceResourceRepository, times(1)).existsByNameAndIdNot("Whiteboard Updated", 1L);
        verify(spaceResourceRepository, never()).save(any(SpaceResource.class));
    }
}
