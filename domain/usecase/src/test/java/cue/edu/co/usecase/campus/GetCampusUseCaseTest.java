package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.GetCampusQuery;
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
class GetCampusUseCaseTest {

    @Mock
    private CampusRepository campusRepository;

    @InjectMocks
    private GetCampusUseCase getCampusUseCase;

    private GetCampusQuery query;
    private Campus campus;

    @BeforeEach
    void setUp() {
        query = new GetCampusQuery(1L);

        campus = Campus.builder()
                .id(1L)
                .name("Main Campus")
                .address("123 University Ave")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetCampusSuccessfully() {
        // Arrange
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));

        // Act
        Campus result = getCampusUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Main Campus", result.getName());
        assertEquals("123 University Ave", result.getAddress());
        assertNotNull(result.getCreatedAt());

        verify(campusRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCampusNotFound() {
        // Arrange
        when(campusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CampusNotFoundException.class, () -> getCampusUseCase.execute(query));

        verify(campusRepository, times(1)).findById(1L);
    }
}
