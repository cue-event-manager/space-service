package cue.edu.co.seeder.seeders;

import cue.edu.co.model.spacestatus.SpaceStatus;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import cue.edu.co.seeder.constants.SpaceStatusSeedConstant;
import cue.edu.co.seeder.constants.SpaceStatusSeederLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class SpaceStatusSeeder implements CommandLineRunner {

    private final SpaceStatusRepository spaceStatusRepository;

    @Override
    public void run(String... args) {
        log.info(SpaceStatusSeederLog.STARTING.getMessage());

        for (SpaceStatusSeedConstant seed : SpaceStatusSeedConstant.values()) {
            boolean exists = spaceStatusRepository.existsByName(seed.getName());
            if (exists) {
                log.info(SpaceStatusSeederLog.STATUS_EXISTS.getMessage(), seed.getName());
                continue;
            }

            SpaceStatus status = SpaceStatus.builder()
                    .name(seed.getName())
                    .description(seed.getDescription())
                    .canBeReserved(seed.getCanBeReserved())
                    .build();

            spaceStatusRepository.save(status);
            log.info(SpaceStatusSeederLog.STATUS_CREATED.getMessage(), seed.getName());
        }

        log.info(SpaceStatusSeederLog.FINISHED.getMessage());
    }
}
