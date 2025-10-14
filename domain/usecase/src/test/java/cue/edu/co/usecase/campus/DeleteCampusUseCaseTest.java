package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.commands.DeleteCampusCommand;
import cue.edu.co.model.campus.exceptions.CampusNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCampusUseCaseTest {

    @Mock
    private CampusRepository campusRepository;

    @InjectMocks
    private DeleteCampusUseCase deleteCampusUseCase;

    private DeleteCampusCommand command;
    private Campus existingCampus;

    @BeforeEach
    void setUp() {
        command = new DeleteCampusCommand(1L);

        existingCampus = Campus.builder()
                .id(1L)
                .name("Main Campus")
                .address("123 University Ave")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldDeleteCampusSuccessfully() {
        // Arrange
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(existingCampus));
        doNothing().when(campusRepository).deleteById(anyLong());

        // Act
        assertDoesNotThrow(() -> deleteCampusUseCase.execute(command));

        // Assert
        verify(campusRepository, times(1)).findById(1L);
        verify(campusRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCampusNotFound() {
        // Arrange
        when(campusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CampusNotFoundException.class, () -> deleteCampusUseCase.execute(command));

        verify(campusRepository, times(1)).findById(1L);
        verify(campusRepository, never()).deleteById(anyLong());
    }
}
