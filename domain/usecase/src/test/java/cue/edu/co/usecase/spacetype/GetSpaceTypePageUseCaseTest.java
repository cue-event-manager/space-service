package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.spacetype.GetSpaceTypePageQuery;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.SpaceTypePageResult;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSpaceTypePageUseCaseTest {

    @Mock
    private SpaceTypeRepository spaceTypeRepository;

    @InjectMocks
    private GetSpaceTypePageUseCase getSpaceTypePageUseCase;

    private GetSpaceTypePageQuery query;
    private List<SpaceType> spaceTypes;
    private SpaceTypePageResult pageResult;

    @BeforeEach
    void setUp() {
        query = GetSpaceTypePageQuery.builder()
                .page(0)
                .size(10)
                .name(null)
                .build();

        spaceTypes = Arrays.asList(
                SpaceType.builder()
                        .id(1L)
                        .name("Classroom")
                        .description("Standard classroom")
                        .createdAt(LocalDateTime.now())
                        .build(),
                SpaceType.builder()
                        .id(2L)
                        .name("Laboratory")
                        .description("Science lab")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        pageResult = SpaceTypePageResult.builder()
                .content(spaceTypes)
                .page(0)
                .size(10)
                .totalElements(2L)
                .totalPages(1)
                .build();
    }

    @Test
    void shouldGetSpaceTypePageSuccessfully() {
        // Arrange
        when(spaceTypeRepository.findAll(any(GetSpaceTypePageQuery.class))).thenReturn(pageResult);

        // Act
        SpaceTypePageResult result = getSpaceTypePageUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(2L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        verify(spaceTypeRepository, times(1)).findAll(query);
    }

    @Test
    void shouldGetEmptyPageWhenNoSpaceTypes() {
        // Arrange
        SpaceTypePageResult emptyResult = SpaceTypePageResult.builder()
                .content(Arrays.asList())
                .page(0)
                .size(10)
                .totalElements(0L)
                .totalPages(0)
                .build();

        when(spaceTypeRepository.findAll(any(GetSpaceTypePageQuery.class))).thenReturn(emptyResult);

        // Act
        SpaceTypePageResult result = getSpaceTypePageUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0L, result.getTotalElements());

        verify(spaceTypeRepository, times(1)).findAll(query);
    }

    @Test
    void shouldGetFilteredPageByName() {
        // Arrange
        GetSpaceTypePageQuery filteredQuery = GetSpaceTypePageQuery.builder()
                .page(0)
                .size(10)
                .name("Lab")
                .build();

        List<SpaceType> filteredSpaceTypes = Arrays.asList(
                SpaceType.builder()
                        .id(2L)
                        .name("Laboratory")
                        .description("Science lab")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        SpaceTypePageResult filteredResult = SpaceTypePageResult.builder()
                .content(filteredSpaceTypes)
                .page(0)
                .size(10)
                .totalElements(1L)
                .totalPages(1)
                .build();

        when(spaceTypeRepository.findAll(any(GetSpaceTypePageQuery.class))).thenReturn(filteredResult);

        // Act
        SpaceTypePageResult result = getSpaceTypePageUseCase.execute(filteredQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Laboratory", result.getContent().get(0).getName());
        assertEquals(1L, result.getTotalElements());

        verify(spaceTypeRepository, times(1)).findAll(filteredQuery);
    }
}
