package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.commands.UpdateCampusCommand;
import cue.edu.co.model.campus.exceptions.CampusNotFoundException;
import cue.edu.co.model.campus.exceptions.DuplicateCampusNameException;
import cue.edu.co.model.campus.gateways.CampusRepository;
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
class UpdateCampusUseCaseTest {

    @Mock
    private CampusRepository campusRepository;

    @InjectMocks
    private UpdateCampusUseCase updateCampusUseCase;

    private UpdateCampusCommand command;
    private Campus existingCampus;

    @BeforeEach
    void setUp() {
        command = new UpdateCampusCommand(1L, "Updated Campus", "456 New Address");

        existingCampus = Campus.builder()
                .id(1L)
                .name("Main Campus")
                .address("123 University Ave")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
    }

    @Test
    void shouldUpdateCampusSuccessfully() {
        // Arrange
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(existingCampus));
        when(campusRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(campusRepository.save(any(Campus.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Campus result = updateCampusUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Campus", result.getName());
        assertEquals("456 New Address", result.getAddress());
        assertEquals(existingCampus.getCreatedAt(), result.getCreatedAt());

        verify(campusRepository, times(1)).findById(1L);
        verify(campusRepository, times(1)).existsByNameAndIdNot("Updated Campus", 1L);
        verify(campusRepository, times(1)).save(any(Campus.class));
    }

    @Test
    void shouldThrowExceptionWhenCampusNotFound() {
        // Arrange
        when(campusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CampusNotFoundException.class, () -> updateCampusUseCase.execute(command));

        verify(campusRepository, times(1)).findById(1L);
        verify(campusRepository, never()).existsByNameAndIdNot(anyString(), anyLong());
        verify(campusRepository, never()).save(any(Campus.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExistsForAnotherCampus() {
        // Arrange
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(existingCampus));
        when(campusRepository.existsByNameAndIdNot(anyString(), anyLong())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateCampusNameException.class, () -> updateCampusUseCase.execute(command));

        verify(campusRepository, times(1)).findById(1L);
        verify(campusRepository, times(1)).existsByNameAndIdNot("Updated Campus", 1L);
        verify(campusRepository, never()).save(any(Campus.class));
    }
}
