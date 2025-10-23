package cue.edu.co.seeder.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceResourceSeedConstant {

    PROJECTOR("Proyector", "Dispositivo para presentación de diapositivas o videos."),
    SOUND_SYSTEM("Sistema de sonido", "Equipo de audio para conferencias y eventos."),
    INTERNET("Conexión a Internet", "Acceso a red inalámbrica institucional."),
    WHITEBOARD("Tablero acrílico", "Superficie blanca para uso con marcadores borrables."),
    AIR_CONDITIONER("Aire acondicionado", "Sistema de climatización del espacio."),
    MICROPHONE("Micrófono", "Equipo para amplificar la voz del expositor."),
    COMPUTER("Computador", "Equipo de cómputo disponible en el espacio."),
    CAMERA("Cámara", "Dispositivo para grabar o transmitir los eventos."),
    TV_SCREEN("Pantalla TV", "Pantalla de gran formato para presentaciones."),
    LIGHTING("Iluminación especial", "Sistema de luces ajustables para presentaciones.");

    private final String name;
    private final String description;
}