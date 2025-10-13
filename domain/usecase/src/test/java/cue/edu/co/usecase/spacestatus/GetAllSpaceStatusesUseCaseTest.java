package cue.edu.co.usecase.spacestatus;

import cue.edu.co.model.common.queries.PaginationQuery;
import cue.edu.co.model.common.enums.SortDirection;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.queries.SpaceStatusPaginationQuery;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
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
class GetAllSpaceStatusesUseCaseTest {

    @Mock
    private SpaceStatusRepository spaceStatusRepository;

    @InjectMocks
    private GetAllSpaceStatusesUseCase getAllSpaceStatusesUseCase;

    private SpaceStatusPaginationQuery query;
    private List<SpaceStatus> spaceStatuses;

    @BeforeEach
    void setUp() {
        PaginationQuery paginationQuery = new PaginationQuery(0, 10, "name", SortDirection.ASC);
        query = new SpaceStatusPaginationQuery(Optional.empty(), paginationQuery);

        spaceStatuses = List.of(
                SpaceStatus.builder()
                        .id(1L)
                        .name("Available")
                        .description("Space is available")
                        .canBeReserved(true)
                        .createdAt(LocalDateTime.now())
                        .build(),
                SpaceStatus.builder()
                        .id(2L)
                        .name("Occupied")
                        .description("Space is occupied")
                        .canBeReserved(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Test
    void shouldGetAllSpaceStatusesSuccessfully() {
        // Arrange
        PageResult<SpaceStatus> pageResult = new PageResult<>(
                spaceStatuses, 0, 10, 2, 1, false, false
        );
        when(spaceStatusRepository.findAllByFilters(any(SpaceStatusPaginationQuery.class)))
                .thenReturn(pageResult);

        // Act
        PageResult<SpaceStatus> result = getAllSpaceStatusesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.items().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(1, result.totalPages());
        assertFalse(result.hasNext());
        assertFalse(result.hasPrevious());

        verify(spaceStatusRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldGetEmptyResultWhenNoSpaceStatusesFound() {
        // Arrange
        PageResult<SpaceStatus> emptyPageResult = new PageResult<>(
                List.of(), 0, 10, 0, 0, false, false
        );
        when(spaceStatusRepository.findAllByFilters(any(SpaceStatusPaginationQuery.class)))
                .thenReturn(emptyPageResult);

        // Act
        PageResult<SpaceStatus> result = getAllSpaceStatusesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.items().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(spaceStatusRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldFilterSpaceStatusesByName() {
        // Arrange
        PaginationQuery paginationQuery = new PaginationQuery(0, 10, "name", SortDirection.ASC);
        SpaceStatusPaginationQuery filteredQuery = new SpaceStatusPaginationQuery(
                Optional.of("Available"), paginationQuery
        );

        List<SpaceStatus> filteredStatuses = List.of(spaceStatuses.get(0));
        PageResult<SpaceStatus> pageResult = new PageResult<>(
                filteredStatuses, 0, 10, 1, 1, false, false
        );

        when(spaceStatusRepository.findAllByFilters(any(SpaceStatusPaginationQuery.class)))
                .thenReturn(pageResult);

        // Act
        PageResult<SpaceStatus> result = getAllSpaceStatusesUseCase.execute(filteredQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals("Available", result.items().get(0).getName());

        verify(spaceStatusRepository, times(1)).findAllByFilters(filteredQuery);
    }

    @Test
    void shouldHandlePaginationCorrectly() {
        // Arrange
        PaginationQuery paginationQuery = new PaginationQuery(1, 1, "name", SortDirection.ASC);
        SpaceStatusPaginationQuery paginatedQuery = new SpaceStatusPaginationQuery(
                Optional.empty(), paginationQuery
        );

        List<SpaceStatus> secondPageStatuses = List.of(spaceStatuses.get(1));
        PageResult<SpaceStatus> pageResult = new PageResult<>(
                secondPageStatuses, 1, 1, 2, 2, false, true
        );

        when(spaceStatusRepository.findAllByFilters(any(SpaceStatusPaginationQuery.class)))
                .thenReturn(pageResult);

        // Act
        PageResult<SpaceStatus> result = getAllSpaceStatusesUseCase.execute(paginatedQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals(1, result.page());
        assertEquals(1, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(2, result.totalPages());
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());

        verify(spaceStatusRepository, times(1)).findAllByFilters(paginatedQuery);
    }
}
