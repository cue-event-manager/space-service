package cue.edu.co.seeder.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceTypeSeedConstant {

    CLASSROOM("Aula", "Espacio destinado a clases presenciales."),
    LABORATORY("Laboratorio", "Espacio equipado para prácticas experimentales o técnicas."),
    AUDITORIUM("Auditorio", "Espacio amplio para conferencias o eventos institucionales."),
    MEETING_ROOM("Sala de reuniones", "Espacio cerrado para reuniones administrativas o académicas."),
    COMPUTER_ROOM("Sala de cómputo", "Espacio con computadores para clases o talleres tecnológicos."),
    LIBRARY("Biblioteca", "Espacio destinado a estudio, lectura y consulta de materiales académicos."),
    SPORTS_AREA("Área deportiva", "Zona destinada a actividades físicas y recreativas."),
    OFFICE("Oficina", "Espacio administrativo o docente para labores individuales o de coordinación."),
    MULTIPURPOSE_ROOM("Sala múltiple", "Espacio adaptable para diferentes tipos de actividades."),
    LABORATORY_HEALTH("Laboratorio de salud", "Espacio para prácticas médicas, clínicas o de enfermería.");

    private final String name;
    private final String description;
}
