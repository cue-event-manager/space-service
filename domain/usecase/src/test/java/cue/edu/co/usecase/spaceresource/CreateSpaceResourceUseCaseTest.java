package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.spaceresource.CreateSpaceResourceCommand;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.exceptions.DuplicateSpaceResourceNameException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
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
class CreateSpaceResourceUseCaseTest {

    @Mock
    private SpaceResourceRepository spaceResourceRepository;

    @InjectMocks
    private CreateSpaceResourceUseCase createSpaceResourceUseCase;

    private CreateSpaceResourceCommand command;

    @BeforeEach
    void setUp() {
        command = new CreateSpaceResourceCommand("Projector", "HD multimedia projector");
    }

    @Test
    void shouldCreateSpaceResourceSuccessfully() {
        // Arrange
        when(spaceResourceRepository.existsByName(anyString())).thenReturn(false);
        when(spaceResourceRepository.save(any(SpaceResource.class))).thenAnswer(invocation -> {
            SpaceResource spaceResource = invocation.getArgument(0);
            return SpaceResource.builder()
                    .id(1L)
                    .name(spaceResource.getName())
                    .description(spaceResource.getDescription())
                    .createdAt(spaceResource.getCreatedAt())
                    .build();
        });

        // Act
        SpaceResource result = createSpaceResourceUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Projector", result.getName());
        assertEquals("HD multimedia projector", result.getDescription());
        assertNotNull(result.getCreatedAt());

        verify(spaceResourceRepository, times(1)).existsByName("Projector");
        verify(spaceResourceRepository, times(1)).save(any(SpaceResource.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExists() {
        // Arrange
        when(spaceResourceRepository.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        DuplicateSpaceResourceNameException exception = assertThrows(
                DuplicateSpaceResourceNameException.class,
                () -> createSpaceResourceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("Projector"));
        verify(spaceResourceRepository, times(1)).existsByName("Projector");
        verify(spaceResourceRepository, never()).save(any(SpaceResource.class));
    }
}
