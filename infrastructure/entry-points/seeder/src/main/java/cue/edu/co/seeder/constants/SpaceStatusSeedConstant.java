package cue.edu.co.seeder.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceStatusSeedConstant {

    AVAILABLE("Disponible", "Espacio libre y disponible para reserva.", true),
    MAINTENANCE("En mantenimiento", "Espacio temporalmente fuera de servicio por mantenimiento.", false),
    INACTIVE("Inactivo", "Espacio deshabilitado para su uso o reserva.", false);

    private final String name;
    private final String description;
    private final Boolean canBeReserved;
}