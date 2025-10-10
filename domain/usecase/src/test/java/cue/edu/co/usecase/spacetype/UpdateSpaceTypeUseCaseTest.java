package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.UpdateSpaceTypeCommand;
import cue.edu.co.model.spacetype.exceptions.DuplicateSpaceTypeNameException;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSpaceTypeUseCaseTest {

    @Mock
    private SpaceTypeRepository spaceTypeRepository;

    @InjectMocks
    private UpdateSpaceTypeUseCase updateSpaceTypeUseCase;

    private UpdateSpaceTypeCommand command;
    private SpaceType existingSpaceType;

    @BeforeEach
    void setUp() {
        command = new UpdateSpaceTypeCommand(1L, "Laboratory Updated", "Updated description");

        existingSpaceType = SpaceType.builder()
                .id(1L)
                .name("Laboratory")
                .description("Original description")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
    }

    @Test
    void shouldUpdateSpaceTypeSuccessfully() {
        // Arrange
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceType));
        when(spaceTypeRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(spaceTypeRepository.save(any(SpaceType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        SpaceType result = updateSpaceTypeUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laboratory Updated", result.getName());
        assertEquals("Updated description", result.getDescription());
        assertEquals(existingSpaceType.getCreatedAt(), result.getCreatedAt());

        verify(spaceTypeRepository, times(1)).findById(1L);
        verify(spaceTypeRepository, times(1)).existsByNameAndIdNot("Laboratory Updated", 1L);
        verify(spaceTypeRepository, times(1)).save(any(SpaceType.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceTypeNotFound() {
        // Arrange
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceTypeNotFoundException.class, () -> updateSpaceTypeUseCase.execute(command));

        verify(spaceTypeRepository, times(1)).findById(1L);
        verify(spaceTypeRepository, never()).existsByNameAndIdNot(anyString(), anyLong());
        verify(spaceTypeRepository, never()).save(any(SpaceType.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExistsForAnotherSpaceType() {
        // Arrange
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(existingSpaceType));
        when(spaceTypeRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateSpaceTypeNameException.class, () -> updateSpaceTypeUseCase.execute(command));

        verify(spaceTypeRepository, times(1)).findById(1L);
        verify(spaceTypeRepository, times(1)).existsByNameAndIdNot("Laboratory Updated", 1L);
        verify(spaceTypeRepository, never()).save(any(SpaceType.class));
    }
}
