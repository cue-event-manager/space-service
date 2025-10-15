package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.commands.CreateCampusCommand;
import cue.edu.co.model.campus.exceptions.DuplicateCampusNameException;
import cue.edu.co.model.campus.gateways.CampusRepository;
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
class CreateCampusUseCaseTest {

    @Mock
    private CampusRepository campusRepository;

    @InjectMocks
    private CreateCampusUseCase createCampusUseCase;

    private CreateCampusCommand command;

    @BeforeEach
    void setUp() {
        command = new CreateCampusCommand("Main Campus", "123 University Ave");
    }

    @Test
    void shouldCreateCampusSuccessfully() {
        // Arrange
        when(campusRepository.existsByName(anyString())).thenReturn(false);
        when(campusRepository.save(any(Campus.class))).thenAnswer(invocation -> {
            Campus campus = invocation.getArgument(0);
            return Campus.builder()
                    .id(1L)
                    .name(campus.getName())
                    .address(campus.getAddress())
                    .createdAt(campus.getCreatedAt())
                    .build();
        });

        // Act
        Campus result = createCampusUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Main Campus", result.getName());
        assertEquals("123 University Ave", result.getAddress());
        assertNotNull(result.getCreatedAt());

        verify(campusRepository, times(1)).existsByName("Main Campus");
        verify(campusRepository, times(1)).save(any(Campus.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExists() {
        // Arrange
        when(campusRepository.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        DuplicateCampusNameException exception = assertThrows(
                DuplicateCampusNameException.class,
                () -> createCampusUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("Main Campus"));
        verify(campusRepository, times(1)).existsByName("Main Campus");
        verify(campusRepository, never()).save(any(Campus.class));
    }
}
