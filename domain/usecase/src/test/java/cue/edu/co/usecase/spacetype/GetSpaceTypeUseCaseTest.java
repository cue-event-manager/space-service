package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.GetSpaceTypeQuery;
import cue.edu.co.model.spacetype.SpaceType;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSpaceTypeUseCaseTest {

    @Mock
    private SpaceTypeRepository spaceTypeRepository;

    @InjectMocks
    private GetSpaceTypeUseCase getSpaceTypeUseCase;

    private GetSpaceTypeQuery query;
    private SpaceType spaceType;

    @BeforeEach
    void setUp() {
        query = new GetSpaceTypeQuery(1L);

        spaceType = SpaceType.builder()
                .id(1L)
                .name("Conference Room")
                .description("Meeting space")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetSpaceTypeSuccessfully() {
        // Arrange
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(spaceType));

        // Act
        SpaceType result = getSpaceTypeUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Conference Room", result.getName());
        assertEquals("Meeting space", result.getDescription());
        assertNotNull(result.getCreatedAt());

        verify(spaceTypeRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSpaceTypeNotFound() {
        // Arrange
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceTypeNotFoundException.class, () -> getSpaceTypeUseCase.execute(query));

        verify(spaceTypeRepository, times(1)).findById(1L);
    }
}
