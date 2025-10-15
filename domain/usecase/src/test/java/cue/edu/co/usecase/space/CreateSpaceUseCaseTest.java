package cue.edu.co.usecase.space;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.exceptions.CampusNotFoundException;
import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.commands.CreateSpaceCommand;
import cue.edu.co.model.space.exceptions.DuplicateSpaceNameException;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.exceptions.SpaceResourceNotFoundException;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.exceptions.SpaceStatusNotFoundException;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSpaceUseCaseTest {

    @Mock
    private SpaceRepository spaceRepository;

    @Mock
    private CampusRepository campusRepository;

    @Mock
    private SpaceTypeRepository spaceTypeRepository;

    @Mock
    private SpaceStatusRepository spaceStatusRepository;

    @Mock
    private SpaceResourceRepository spaceResourceRepository;

    @InjectMocks
    private CreateSpaceUseCase createSpaceUseCase;

    private CreateSpaceCommand command;
    private Campus campus;
    private SpaceType spaceType;
    private SpaceStatus spaceStatus;
    private SpaceResource spaceResource;

    @BeforeEach
    void setUp() {
        command = new CreateSpaceCommand(
                "Auditorium A",
                1L,
                1L,
                1L,
                200,
                Set.of(1L)
        );

        campus = Campus.builder()
                .id(1L)
                .name("Main Campus")
                .address("123 Main St")
                .createdAt(LocalDateTime.now())
                .build();

        spaceType = SpaceType.builder()
                .id(1L)
                .name("Auditorium")
                .description("Large auditorium")
                .createdAt(LocalDateTime.now())
                .build();

        spaceStatus = SpaceStatus.builder()
                .id(1L)
                .name("Available")
                .description("Available for reservation")
                .canBeReserved(true)
                .createdAt(LocalDateTime.now())
                .build();

        spaceResource = SpaceResource.builder()
                .id(1L)
                .name("Projector")
                .description("HD projector")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCreateSpaceSuccessfully() {
        // Arrange
        when(spaceRepository.existsByNameAndCampusId(anyString(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(spaceType));
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(spaceStatus));
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.of(spaceResource));
        when(spaceRepository.save(any(Space.class))).thenAnswer(invocation -> {
            Space space = invocation.getArgument(0);
            return Space.builder()
                    .id(1L)
                    .name(space.getName())
                    .campus(space.getCampus())
                    .type(space.getType())
                    .status(space.getStatus())
                    .capacity(space.getCapacity())
                    .resources(space.getResources())
                    .createdAt(space.getCreatedAt())
                    .build();
        });

        // Act
        Space result = createSpaceUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Auditorium A", result.getName());
        assertEquals(200, result.getCapacity());
        assertEquals(campus, result.getCampus());
        assertEquals(spaceType, result.getType());
        assertEquals(spaceStatus, result.getStatus());
        assertEquals(1, result.getResources().size());
        assertNotNull(result.getCreatedAt());

        verify(spaceRepository, times(1)).existsByNameAndCampusId("Auditorium A", 1L);
        verify(campusRepository, times(1)).findById(1L);
        verify(spaceTypeRepository, times(1)).findById(1L);
        verify(spaceStatusRepository, times(1)).findById(1L);
        verify(spaceResourceRepository, times(1)).findById(1L);
        verify(spaceRepository, times(1)).save(any(Space.class));
    }

    @Test
    void shouldCreateSpaceWithoutResources() {
        // Arrange
        CreateSpaceCommand commandWithoutResources = new CreateSpaceCommand(
                "Classroom B",
                1L,
                1L,
                1L,
                30,
                null
        );

        when(spaceRepository.existsByNameAndCampusId(anyString(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(spaceType));
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(spaceStatus));
        when(spaceRepository.save(any(Space.class))).thenAnswer(invocation -> {
            Space space = invocation.getArgument(0);
            return Space.builder()
                    .id(2L)
                    .name(space.getName())
                    .campus(space.getCampus())
                    .type(space.getType())
                    .status(space.getStatus())
                    .capacity(space.getCapacity())
                    .resources(space.getResources())
                    .createdAt(space.getCreatedAt())
                    .build();
        });

        // Act
        Space result = createSpaceUseCase.execute(commandWithoutResources);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Classroom B", result.getName());
        assertTrue(result.getResources().isEmpty());

        verify(spaceResourceRepository, never()).findById(anyLong());
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExistsInCampus() {
        // Arrange
        when(spaceRepository.existsByNameAndCampusId(anyString(), anyLong())).thenReturn(true);

        // Act & Assert
        DuplicateSpaceNameException exception = assertThrows(
                DuplicateSpaceNameException.class,
                () -> createSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("Auditorium A"));
        verify(spaceRepository, times(1)).existsByNameAndCampusId("Auditorium A", 1L);
        verify(campusRepository, never()).findById(anyLong());
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenCampusNotFound() {
        // Arrange
        when(spaceRepository.existsByNameAndCampusId(anyString(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CampusNotFoundException exception = assertThrows(
                CampusNotFoundException.class,
                () -> createSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(campusRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceTypeNotFound() {
        // Arrange
        when(spaceRepository.existsByNameAndCampusId(anyString(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceTypeNotFoundException exception = assertThrows(
                SpaceTypeNotFoundException.class,
                () -> createSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceTypeRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceStatusNotFound() {
        // Arrange
        when(spaceRepository.existsByNameAndCampusId(anyString(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(spaceType));
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceStatusNotFoundException exception = assertThrows(
                SpaceStatusNotFoundException.class,
                () -> createSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceStatusRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceResourceNotFound() {
        // Arrange
        when(spaceRepository.existsByNameAndCampusId(anyString(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(spaceType));
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(spaceStatus));
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceResourceNotFoundException exception = assertThrows(
                SpaceResourceNotFoundException.class,
                () -> createSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceResourceRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }
}
