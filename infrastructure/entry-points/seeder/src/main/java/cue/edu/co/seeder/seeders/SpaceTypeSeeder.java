package cue.edu.co.seeder.seeders;

import cue.edu.co.model.spacetype.SpaceType;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import cue.edu.co.seeder.constants.SpaceTypeSeedConstant;
import cue.edu.co.seeder.constants.SpaceTypeSeederLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(4)
public class SpaceTypeSeeder implements CommandLineRunner {

    private final SpaceTypeRepository spaceTypeRepository;

    @Override
    public void run(String... args) {
        log.info(SpaceTypeSeederLog.STARTING.getMessage());

        for (SpaceTypeSeedConstant seed : SpaceTypeSeedConstant.values()) {
            boolean exists = spaceTypeRepository.existsByName(seed.getName());
            if (exists) {
                log.info(SpaceTypeSeederLog.TYPE_EXISTS.getMessage(), seed.getName());
                continue;
            }

            SpaceType type = SpaceType.builder()
                    .name(seed.getName())
                    .description(seed.getDescription())
                    .build();

            spaceTypeRepository.save(type);
            log.info(SpaceTypeSeederLog.TYPE_CREATED.getMessage(), seed.getName());
        }

        log.info(SpaceTypeSeederLog.FINISHED.getMessage());
    }
}
