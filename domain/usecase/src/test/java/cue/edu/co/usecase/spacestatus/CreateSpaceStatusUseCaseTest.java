package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.commands.CreateSpaceStatusCommand;
import cue.edu.co.model.spacestatus.exceptions.DuplicateSpaceStatusNameException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSpaceStatusUseCaseTest {

    @Mock
    private SpaceStatusRepository spaceStatusRepository;

    @InjectMocks
    private CreateSpaceStatusUseCase createSpaceStatusUseCase;

    private CreateSpaceStatusCommand command;

    @BeforeEach
    void setUp() {
        command = new CreateSpaceStatusCommand("Available", "Space is available for reservation", true);
    }

    @Test
    void shouldCreateSpaceStatusSuccessfully() {
        // Arrange
        when(spaceStatusRepository.existsByName(anyString())).thenReturn(false);
        when(spaceStatusRepository.save(any(SpaceStatus.class))).thenAnswer(invocation -> {
            SpaceStatus spaceStatus = invocation.getArgument(0);
            return SpaceStatus.builder()
                    .id(1L)
                    .name(spaceStatus.getName())
                    .description(spaceStatus.getDescription())
                    .canBeReserved(spaceStatus.getCanBeReserved())
                    .createdAt(spaceStatus.getCreatedAt())
                    .build();
        });

        // Act
        SpaceStatus result = createSpaceStatusUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Available", result.getName());
        assertEquals("Space is available for reservation", result.getDescription());
        assertTrue(result.getCanBeReserved());
        assertNotNull(result.getCreatedAt());

        verify(spaceStatusRepository, times(1)).existsByName("Available");
        verify(spaceStatusRepository, times(1)).save(any(SpaceStatus.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExists() {
        // Arrange
        when(spaceStatusRepository.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        DuplicateSpaceStatusNameException exception = assertThrows(
                DuplicateSpaceStatusNameException.class,
                () -> createSpaceStatusUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("Available"));
        verify(spaceStatusRepository, times(1)).existsByName("Available");
        verify(spaceStatusRepository, never()).save(any(SpaceStatus.class));
    }
}
