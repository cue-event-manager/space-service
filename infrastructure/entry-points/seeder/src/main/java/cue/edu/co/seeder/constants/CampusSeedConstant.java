package cue.edu.co.seeder.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampusSeedConstant {

    MAIN_CAMPUS(
            "Sede Principal",
            "Avenida Bolívar N° 1-189 - Armenia, Quindío"
    ),

    ALCAZAR_CAMPUS(
            "Sede Alcázar",
            "Calle 4N # 13-05 - Armenia, Quindío"
    ),

    ANOVA_CAMPUS(
            "Sede Anova (Medicina)",
            "Carrera 13 # 15N -46 - Edificio Anova - Armenia, Quindío"
    ),

    NOGAL_CAMPUS(
            "Sede Nogal",
            "Carrera 13 # 16N -07 - Armenia, Quindío"
    ),

    CASA_ANOVA(
            "Casa Anova",
            "Calle 16N #13-09 - Armenia, Quindío"
    ),

    VETERINARY_CLINIC(
            "Clínica Veterinaria Universitaria Humboldt",
            "Calle 6N # 14-26 - Armenia, Quindío"
    );

    private final String name;
    private final String address;
}
