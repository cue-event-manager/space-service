package cue.edu.co.usecase.spacetype;

import cue.edu.co.model.common.enums.SortDirection;
import cue.edu.co.model.common.queries.PaginationQuery;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import cue.edu.co.model.spacetype.queries.SpaceTypePaginationQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllSpaceTypesUseCaseTest {

    @Mock
    private SpaceTypeRepository spaceTypeRepository;

    @InjectMocks
    private GetAllSpaceTypesUseCase getAllSpaceTypesUseCase;

    private SpaceTypePaginationQuery query;
    private PageResult<SpaceType> pageResult;
    private List<SpaceType> spaceTypes;

    @BeforeEach
    void setUp() {
        PaginationQuery paginationQuery = PaginationQuery.builder()
                .page(0)
                .size(10)
                .sortBy("createdAt")
                .sortDirection(SortDirection.ASC)
                .build();

        query = SpaceTypePaginationQuery.builder()
                .name(Optional.empty())
                .pagination(paginationQuery)
                .build();

        spaceTypes = List.of(
                SpaceType.builder()
                        .id(1L)
                        .name("Conference Room")
                        .description("Meeting space")
                        .createdAt(LocalDateTime.now())
                        .build(),
                SpaceType.builder()
                        .id(2L)
                        .name("Auditorium")
                        .description("Large events space")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        pageResult = PageResult.<SpaceType>builder()
                .items(spaceTypes)
                .page(0)
                .size(10)
                .totalElements(2)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();
    }

    @Test
    void shouldGetAllSpaceTypesSuccessfully() {
        // Arrange
        when(spaceTypeRepository.findAllByFilters(any(SpaceTypePaginationQuery.class)))
                .thenReturn(pageResult);

        // Act
        PageResult<SpaceType> result = getAllSpaceTypesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.items().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(1, result.totalPages());
        assertFalse(result.hasNext());
        assertFalse(result.hasPrevious());

        assertEquals("Conference Room", result.items().get(0).getName());
        assertEquals("Auditorium", result.items().get(1).getName());

        verify(spaceTypeRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldGetSpaceTypesWithNameFilter() {
        // Arrange
        SpaceTypePaginationQuery queryWithFilter = SpaceTypePaginationQuery.builder()
                .name(Optional.of("Conference"))
                .pagination(query.pagination())
                .build();

        List<SpaceType> filteredSpaceTypes = List.of(spaceTypes.get(0));

        PageResult<SpaceType> filteredPageResult = PageResult.<SpaceType>builder()
                .items(filteredSpaceTypes)
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(spaceTypeRepository.findAllByFilters(any(SpaceTypePaginationQuery.class)))
                .thenReturn(filteredPageResult);

        // Act
        PageResult<SpaceType> result = getAllSpaceTypesUseCase.execute(queryWithFilter);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals("Conference Room", result.items().get(0).getName());
        assertEquals(1, result.totalElements());

        verify(spaceTypeRepository, times(1)).findAllByFilters(queryWithFilter);
    }

    @Test
    void shouldReturnEmptyPageWhenNoSpaceTypesFound() {
        // Arrange
        PageResult<SpaceType> emptyPageResult = PageResult.<SpaceType>builder()
                .items(List.of())
                .page(0)
                .size(10)
                .totalElements(0)
                .totalPages(0)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(spaceTypeRepository.findAllByFilters(any(SpaceTypePaginationQuery.class)))
                .thenReturn(emptyPageResult);

        // Act
        PageResult<SpaceType> result = getAllSpaceTypesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.items().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(spaceTypeRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldHandlePaginationCorrectly() {
        // Arrange
        PaginationQuery paginationQuery = PaginationQuery.builder()
                .page(1)
                .size(1)
                .sortBy("name")
                .sortDirection(SortDirection.DESC)
                .build();

        SpaceTypePaginationQuery paginatedQuery = SpaceTypePaginationQuery.builder()
                .name(Optional.empty())
                .pagination(paginationQuery)
                .build();

        PageResult<SpaceType> paginatedResult = PageResult.<SpaceType>builder()
                .items(List.of(spaceTypes.get(1)))
                .page(1)
                .size(1)
                .totalElements(2)
                .totalPages(2)
                .hasNext(false)
                .hasPrevious(true)
                .build();

        when(spaceTypeRepository.findAllByFilters(any(SpaceTypePaginationQuery.class)))
                .thenReturn(paginatedResult);

        // Act
        PageResult<SpaceType> result = getAllSpaceTypesUseCase.execute(paginatedQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals(1, result.page());
        assertEquals(1, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(2, result.totalPages());
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());

        verify(spaceTypeRepository, times(1)).findAllByFilters(paginatedQuery);
    }
}
