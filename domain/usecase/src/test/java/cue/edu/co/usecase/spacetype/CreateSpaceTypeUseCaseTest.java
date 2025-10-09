package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.CreateSpaceTypeCommand;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.exceptions.DuplicateSpaceTypeNameException;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSpaceTypeUseCaseTest {

    @Mock
    private SpaceTypeRepository spaceTypeRepository;

    @InjectMocks
    private CreateSpaceTypeUseCase createSpaceTypeUseCase;

    private CreateSpaceTypeCommand command;

    @BeforeEach
    void setUp() {
        command = CreateSpaceTypeCommand.builder()
                .name("Classroom")
                .description("Standard classroom")
                .build();
    }

    @Test
    void shouldCreateSpaceTypeSuccessfully() {
        // Arrange
        when(spaceTypeRepository.existsByName(anyString())).thenReturn(false);
        when(spaceTypeRepository.save(any(SpaceType.class))).thenAnswer(invocation -> {
            SpaceType spaceType = invocation.getArgument(0);
            return SpaceType.builder()
                    .id(1L)
                    .name(spaceType.getName())
                    .description(spaceType.getDescription())
                    .createdAt(spaceType.getCreatedAt())
                    .build();
        });

        // Act
        SpaceType result = createSpaceTypeUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Classroom", result.getName());
        assertEquals("Standard classroom", result.getDescription());
        assertNotNull(result.getCreatedAt());

        verify(spaceTypeRepository, times(1)).existsByName("Classroom");
        verify(spaceTypeRepository, times(1)).save(any(SpaceType.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExists() {
        // Arrange
        when(spaceTypeRepository.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        DuplicateSpaceTypeNameException exception = assertThrows(
                DuplicateSpaceTypeNameException.class,
                () -> createSpaceTypeUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("Classroom"));
        verify(spaceTypeRepository, times(1)).existsByName("Classroom");
        verify(spaceTypeRepository, never()).save(any(SpaceType.class));
    }
}
