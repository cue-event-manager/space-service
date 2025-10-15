package cue.edu.co.usecase.space;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.exceptions.CampusNotFoundException;
import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.model.space.Space;
import cue.edu.co.model.space.commands.UpdateSpaceCommand;
import cue.edu.co.model.space.exceptions.DuplicateSpaceNameException;
import cue.edu.co.model.space.exceptions.SpaceNotFoundException;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSpaceUseCaseTest {

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
    private UpdateSpaceUseCase updateSpaceUseCase;

    private UpdateSpaceCommand command;
    private Space existingSpace;
    private Campus campus;
    private SpaceType spaceType;
    private SpaceStatus spaceStatus;
    private SpaceResource spaceResource;

    @BeforeEach
    void setUp() {
        command = new UpdateSpaceCommand(
                1L,
                "Updated Auditorium",
                1L,
                1L,
                1L,
                250,
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

        existingSpace = Space.builder()
                .id(1L)
                .name("Auditorium A")
                .campus(campus)
                .type(spaceType)
                .status(spaceStatus)
                .capacity(200)
                .resources(new HashSet<>())
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
    }

    @Test
    void shouldUpdateSpaceSuccessfully() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpace));
        when(spaceRepository.existsByNameAndCampusIdAndIdNot(anyString(), anyLong(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(spaceType));
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(spaceStatus));
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.of(spaceResource));
        when(spaceRepository.save(any(Space.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Space result = updateSpaceUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Auditorium", result.getName());
        assertEquals(250, result.getCapacity());
        assertEquals(campus, result.getCampus());
        assertEquals(spaceType, result.getType());
        assertEquals(spaceStatus, result.getStatus());
        assertEquals(1, result.getResources().size());
        assertEquals(existingSpace.getCreatedAt(), result.getCreatedAt());

        verify(spaceRepository, times(1)).findById(1L);
        verify(spaceRepository, times(1)).existsByNameAndCampusIdAndIdNot("Updated Auditorium", 1L, 1L);
        verify(spaceRepository, times(1)).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceNotFound() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceNotFoundException exception = assertThrows(
                SpaceNotFoundException.class,
                () -> updateSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenNameAlreadyExistsInCampus() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpace));
        when(spaceRepository.existsByNameAndCampusIdAndIdNot(anyString(), anyLong(), anyLong())).thenReturn(true);

        // Act & Assert
        DuplicateSpaceNameException exception = assertThrows(
                DuplicateSpaceNameException.class,
                () -> updateSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("Updated Auditorium"));
        verify(spaceRepository, times(1)).existsByNameAndCampusIdAndIdNot("Updated Auditorium", 1L, 1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenCampusNotFound() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpace));
        when(spaceRepository.existsByNameAndCampusIdAndIdNot(anyString(), anyLong(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CampusNotFoundException exception = assertThrows(
                CampusNotFoundException.class,
                () -> updateSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(campusRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceTypeNotFound() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpace));
        when(spaceRepository.existsByNameAndCampusIdAndIdNot(anyString(), anyLong(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceTypeNotFoundException exception = assertThrows(
                SpaceTypeNotFoundException.class,
                () -> updateSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceTypeRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceStatusNotFound() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpace));
        when(spaceRepository.existsByNameAndCampusIdAndIdNot(anyString(), anyLong(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(spaceType));
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceStatusNotFoundException exception = assertThrows(
                SpaceStatusNotFoundException.class,
                () -> updateSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceStatusRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }

    @Test
    void shouldThrowExceptionWhenSpaceResourceNotFound() {
        // Arrange
        when(spaceRepository.findById(anyLong())).thenReturn(Optional.of(existingSpace));
        when(spaceRepository.existsByNameAndCampusIdAndIdNot(anyString(), anyLong(), anyLong())).thenReturn(false);
        when(campusRepository.findById(anyLong())).thenReturn(Optional.of(campus));
        when(spaceTypeRepository.findById(anyLong())).thenReturn(Optional.of(spaceType));
        when(spaceStatusRepository.findById(anyLong())).thenReturn(Optional.of(spaceStatus));
        when(spaceResourceRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        SpaceResourceNotFoundException exception = assertThrows(
                SpaceResourceNotFoundException.class,
                () -> updateSpaceUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("1"));
        verify(spaceResourceRepository, times(1)).findById(1L);
        verify(spaceRepository, never()).save(any(Space.class));
    }
}
