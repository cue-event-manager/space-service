package cue.edu.co.usecase.campus;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.model.campus.queries.CampusPaginationQuery;
import cue.edu.co.model.common.enums.SortDirection;
import cue.edu.co.model.common.queries.PaginationQuery;
import cue.edu.co.model.common.results.PageResult;
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
class GetAllCampusesUseCaseTest {

    @Mock
    private CampusRepository campusRepository;

    @InjectMocks
    private GetAllCampusesUseCase getAllCampusesUseCase;

    private CampusPaginationQuery query;
    private List<Campus> campuses;

    @BeforeEach
    void setUp() {
        PaginationQuery paginationQuery = new PaginationQuery(0, 10, "name", SortDirection.ASC);
        query = new CampusPaginationQuery(Optional.empty(), paginationQuery);

        campuses = List.of(
                Campus.builder()
                        .id(1L)
                        .name("Main Campus")
                        .address("123 University Ave")
                        .createdAt(LocalDateTime.now())
                        .build(),
                Campus.builder()
                        .id(2L)
                        .name("North Campus")
                        .address("456 College Rd")
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Test
    void shouldGetAllCampusesSuccessfully() {
        // Arrange
        PageResult<Campus> pageResult = new PageResult<>(
                campuses, 0, 10, 2, 1, false, false
        );
        when(campusRepository.findAllByFilters(any(CampusPaginationQuery.class)))
                .thenReturn(pageResult);

        // Act
        PageResult<Campus> result = getAllCampusesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.items().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(1, result.totalPages());
        assertFalse(result.hasNext());
        assertFalse(result.hasPrevious());

        verify(campusRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldGetEmptyResultWhenNoCampusesFound() {
        // Arrange
        PageResult<Campus> emptyPageResult = new PageResult<>(
                List.of(), 0, 10, 0, 0, false, false
        );
        when(campusRepository.findAllByFilters(any(CampusPaginationQuery.class)))
                .thenReturn(emptyPageResult);

        // Act
        PageResult<Campus> result = getAllCampusesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.items().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());

        verify(campusRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldFilterCampusesByName() {
        // Arrange
        PaginationQuery paginationQuery = new PaginationQuery(0, 10, "name", SortDirection.ASC);
        CampusPaginationQuery filteredQuery = new CampusPaginationQuery(
                Optional.of("Main"), paginationQuery
        );

        List<Campus> filteredCampuses = List.of(campuses.get(0));
        PageResult<Campus> pageResult = new PageResult<>(
                filteredCampuses, 0, 10, 1, 1, false, false
        );

        when(campusRepository.findAllByFilters(any(CampusPaginationQuery.class)))
                .thenReturn(pageResult);

        // Act
        PageResult<Campus> result = getAllCampusesUseCase.execute(filteredQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals("Main Campus", result.items().get(0).getName());

        verify(campusRepository, times(1)).findAllByFilters(filteredQuery);
    }

    @Test
    void shouldHandlePaginationCorrectly() {
        // Arrange
        PaginationQuery paginationQuery = new PaginationQuery(1, 1, "name", SortDirection.ASC);
        CampusPaginationQuery paginatedQuery = new CampusPaginationQuery(
                Optional.empty(), paginationQuery
        );

        List<Campus> secondPageCampuses = List.of(campuses.get(1));
        PageResult<Campus> pageResult = new PageResult<>(
                secondPageCampuses, 1, 1, 2, 2, false, true
        );

        when(campusRepository.findAllByFilters(any(CampusPaginationQuery.class)))
                .thenReturn(pageResult);

        // Act
        PageResult<Campus> result = getAllCampusesUseCase.execute(paginatedQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals(1, result.page());
        assertEquals(1, result.size());
        assertEquals(2, result.totalElements());
        assertEquals(2, result.totalPages());
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());

        verify(campusRepository, times(1)).findAllByFilters(paginatedQuery);
    }
}
