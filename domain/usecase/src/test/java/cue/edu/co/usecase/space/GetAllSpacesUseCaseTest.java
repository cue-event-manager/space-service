package cue.edu.co.usecase.space;

import cue.edu.co.model.common.enums.SortDirection;
import cue.edu.co.model.common.queries.PaginationQuery;
import cue.edu.co.model.common.results.PageResult;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.space.queries.SpacePaginationQuery;
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
class GetAllSpacesUseCaseTest {

    @Mock
    private SpaceRepository spaceRepository;

    @InjectMocks
    private GetAllSpacesUseCase getAllSpacesUseCase;

    private SpacePaginationQuery query;
    private Space space1;
    private Space space2;

    @BeforeEach
    void setUp() {
        PaginationQuery paginationQuery = new PaginationQuery(0, 10, "name", SortDirection.ASC);
        query = new SpacePaginationQuery(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                paginationQuery
        );

        space1 = Space.builder()
                .id(1L)
                .name("Auditorium A")
                .capacity(200)
                .createdAt(LocalDateTime.now())
                .build();

        space2 = Space.builder()
                .id(2L)
                .name("Classroom B")
                .capacity(30)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetAllSpacesSuccessfully() {
        // Arrange
        PageResult<Space> pageResult = new PageResult<>(
                List.of(space1, space2),
                0,
                10,
                2L,
                1,
                false,
                false
        );
        when(spaceRepository.findAllByFilters(any(SpacePaginationQuery.class))).thenReturn(pageResult);

        // Act
        PageResult<Space> result = getAllSpacesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.items().size());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertEquals(2L, result.totalElements());
        assertEquals(1, result.totalPages());
        assertFalse(result.hasNext());
        assertFalse(result.hasPrevious());

        verify(spaceRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldGetEmptyPageWhenNoSpacesFound() {
        // Arrange
        PageResult<Space> emptyPageResult = new PageResult<>(
                List.of(),
                0,
                10,
                0L,
                0,
                false,
                false
        );
        when(spaceRepository.findAllByFilters(any(SpacePaginationQuery.class))).thenReturn(emptyPageResult);

        // Act
        PageResult<Space> result = getAllSpacesUseCase.execute(query);

        // Assert
        assertNotNull(result);
        assertTrue(result.items().isEmpty());
        assertEquals(0, result.totalElements());

        verify(spaceRepository, times(1)).findAllByFilters(query);
    }

    @Test
    void shouldGetSpacesWithFilters() {
        // Arrange
        PaginationQuery paginationQuery = new PaginationQuery(0, 10, "name", SortDirection.ASC);
        SpacePaginationQuery filteredQuery = new SpacePaginationQuery(
                Optional.of("Auditorium"),
                Optional.of(1L),
                Optional.of(1L),
                Optional.of(1L),
                paginationQuery
        );

        PageResult<Space> pageResult = new PageResult<>(
                List.of(space1),
                0,
                10,
                1L,
                1,
                false,
                false
        );
        when(spaceRepository.findAllByFilters(any(SpacePaginationQuery.class))).thenReturn(pageResult);

        // Act
        PageResult<Space> result = getAllSpacesUseCase.execute(filteredQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals("Auditorium A", result.items().get(0).getName());

        verify(spaceRepository, times(1)).findAllByFilters(filteredQuery);
    }
}
