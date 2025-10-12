package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.spacestatus.GetSpaceStatusQuery;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.exceptions.SpaceStatusNotFoundException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
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
class GetSpaceStatusUseCaseTest {

    @Mock
    private SpaceStatusRepository spaceStatusRepository;

    @InjectMocks
    private GetSpaceStatusUseCase getSpaceStatusUseCase;

    private GetSpaceStatusQuery query;
    private SpaceStatus spaceStatus;

    @BeforeEach
    void setUp() {
        query = new GetSpaceStatusQuery(1L);

        spaceStatus = SpaceStatus.builder()
                .id(1L)
                .name("Available")
                .description("Space is available")
                .canBeReserved(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetSpaceStatusSuccessfully() {
        // Arrange
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(spaceStatus));

        // Act
        SpaceStatus result = getSpaceStatusUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Available", result.getName());
        assertEquals("Space is available", result.getDescription());
        assertTrue(result.getCanBeReserved());
        assertNotNull(result.getCreatedAt());

        verify(spaceStatusRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSpaceStatusNotFound() {
        // Arrange
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceStatusNotFoundException.class, () -> getSpaceStatusUseCase.execute(query));

        verify(spaceStatusRepository, times(1)).findById(1L);
    }
}
