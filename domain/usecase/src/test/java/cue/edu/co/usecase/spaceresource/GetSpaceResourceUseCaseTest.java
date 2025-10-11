package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.spaceresource.GetSpaceResourceQuery;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.exceptions.SpaceResourceNotFoundException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
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
class GetSpaceResourceUseCaseTest {

    @Mock
    private SpaceResourceRepository spaceResourceRepository;

    @InjectMocks
    private GetSpaceResourceUseCase getSpaceResourceUseCase;

    private GetSpaceResourceQuery query;
    private SpaceResource spaceResource;

    @BeforeEach
    void setUp() {
        query = new GetSpaceResourceQuery(1L);

        spaceResource = SpaceResource.builder()
                .id(1L)
                .name("Computer")
                .description("Desktop computer")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetSpaceResourceSuccessfully() {
        // Arrange
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.of(spaceResource));

        // Act
        SpaceResource result = getSpaceResourceUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Computer", result.getName());
        assertEquals("Desktop computer", result.getDescription());
        assertNotNull(result.getCreatedAt());

        verify(spaceResourceRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenSpaceResourceNotFound() {
        // Arrange
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SpaceResourceNotFoundException.class, () -> getSpaceResourceUseCase.execute(query));

        verify(spaceResourceRepository, times(1)).findById(1L);
    }
}
