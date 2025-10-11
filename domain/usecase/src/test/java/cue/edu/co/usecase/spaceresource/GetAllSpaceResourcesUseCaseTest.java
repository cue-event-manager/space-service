package cue.edu.co.usecase.spaceresource;

import cue.edu.co.model.common.enums.SortDirection;
import cue.edu.co.model.common.queries.PaginationQuery;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.model.spaceresource.queries.SpaceResourcePaginationQuery;
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
class GetAllSpaceResourcesUseCaseTest {

    @Mock
    private SpaceResourceRepository spaceResourceRepository;

    @InjectMocks
    private GetAllSpaceResourcesUseCase getAllSpaceResourcesUseCase;

    private SpaceResourcePaginationQuery query;
    private PageResult<SpaceResource> pageResult;
    private List<SpaceResource> spaceResources;

    @BeforeEach
    void setUp() {
        PaginationQuery paginationQuery = PaginationQuery.builder()
                .page(0)
                .size(10)
                .sortBy("createdAt")
                .sortDirection(SortDirection.ASC)
                .build();

        query = SpaceResourcePaginationQuery.builder()
                .name(Optional.empty())
                .pagination(paginationQuery)
                .build();

        spaceResources = List.of(
                SpaceResource.builder()
                        .id(1L)
                        .name("Projector")
                        .description("HD multimedia projector")
                        .createdAt(LocalDateTime.now())
                        .build(),
                SpaceResource.builder()
                        .id(2L)
                        .name("Whiteboard")
                        .description("Interactive whiteboard")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        pageResult = PageResult.<SpaceResource>builder()
                .items(spaceResources)
                .page(0)
                .size(10)
                .totalElements(2)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();
    }

    @Test
    void shouldGetAllSpaceResourcesSuccessfully() {
        // Arrange
        when(spaceResourceRepository.findAllByFilters(any(SpaceResourcePaginationQuery.class)))
                .thenReturn(pageResult);

        // Act
        PageResult<SpaceResource> result = getAllSpaceResourcesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.items().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(1, result.totalPages());
        assertFalse(result.hasNext());
        assertFalse(result.hasPrevious());

        assertEquals("Projector", result.items().get(0).getName());
        assertEquals("Whiteboard", result.items().get(1).getName());

        verify(spaceResourceRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldGetSpaceResourcesWithNameFilter() {
        // Arrange
        SpaceResourcePaginationQuery queryWithFilter = SpaceResourcePaginationQuery.builder()
                .name(Optional.of("Projector"))
                .pagination(query.pagination())
                .build();

        List<SpaceResource> filteredSpaceResources = List.of(spaceResources.get(0));

        PageResult<SpaceResource> filteredPageResult = PageResult.<SpaceResource>builder()
                .items(filteredSpaceResources)
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(spaceResourceRepository.findAllByFilters(any(SpaceResourcePaginationQuery.class)))
                .thenReturn(filteredPageResult);

        // Act
        PageResult<SpaceResource> result = getAllSpaceResourcesUseCase.execute(queryWithFilter);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals("Projector", result.items().get(0).getName());
        assertEquals(1, result.totalElements());

        verify(spaceResourceRepository, times(1)).findAllByFilters(queryWithFilter);
    }

    @Test
    void shouldReturnEmptyPageWhenNoSpaceResourcesFound() {
        // Arrange
        PageResult<SpaceResource> emptyPageResult = PageResult.<SpaceResource>builder()
                .items(List.of())
                .page(0)
                .size(10)
                .totalElements(0)
                .totalPages(0)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(spaceResourceRepository.findAllByFilters(any(SpaceResourcePaginationQuery.class)))
                .thenReturn(emptyPageResult);

        // Act
        PageResult<SpaceResource> result = getAllSpaceResourcesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.items().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(spaceResourceRepository, times(1)).findAllByFilters(query);
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

        SpaceResourcePaginationQuery paginatedQuery = SpaceResourcePaginationQuery.builder()
                .name(Optional.empty())
                .pagination(paginationQuery)
                .build();

        PageResult<SpaceResource> paginatedResult = PageResult.<SpaceResource>builder()
                .items(List.of(spaceResources.get(1)))
                .page(1)
                .size(1)
                .totalElements(2)
                .totalPages(2)
                .hasNext(false)
                .hasPrevious(true)
                .build();

        when(spaceResourceRepository.findAllByFilters(any(SpaceResourcePaginationQuery.class)))
                .thenReturn(paginatedResult);

        // Act
        PageResult<SpaceResource> result = getAllSpaceResourcesUseCase.execute(paginatedQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals(1, result.page());
        assertEquals(1, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(2, result.totalPages());
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());

        verify(spaceResourceRepository, times(1)).findAllByFilters(paginatedQuery);
    }
}
