package cue.edu.co.usecase.space;

import cue.edu.co.model.space.queries.GetSpaceQuery;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
import cue.edu.co.model.space.gateways.SpaceRepository;
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
class GetSpaceUseCaseTest {

    @Mock
    private SpaceRepository spaceRepository;

    @InjectMocks
    private GetSpaceUseCase getSpaceUseCase;

    private GetSpaceQuery query;
    private Space existingSpace;

    @BeforeEach
    void setUp() {
        query = new GetSpaceQuery(1L);

        existingSpace = Space.builder()
                .id(1L)
                .name("Auditorium A")
                .capacity(200)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetSpaceSuccessfully() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpace));

        // Act
        Space result = getSpaceUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Auditorium A", result.getName());
        assertEquals(200, result.getCapacity());
        assertNotNull(result.getCreatedAt());

        verify(spaceRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSpaceNotFound() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceNotFoundException exception = assertThrows(
                SpaceNotFoundException.class,
                () -> getSpaceUseCase.execute(query)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceRepository, times(1)).findById(1L);
    }
}
