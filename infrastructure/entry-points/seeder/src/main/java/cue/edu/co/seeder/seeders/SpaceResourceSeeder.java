package cue.edu.co.seeder.seeders;

import cue.edu.co.model.spaceresource.SpaceResource;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.seeder.constants.SpaceResourceSeedConstant;
import cue.edu.co.seeder.constants.SpaceResourceSeederLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class SpaceResourceSeeder implements CommandLineRunner {

    private final SpaceResourceRepository spaceResourceRepository;

    @Override
    public void run(String... args) {
        log.info(SpaceResourceSeederLog.STARTING.getMessage());

        for (SpaceResourceSeedConstant seed : SpaceResourceSeedConstant.values()) {
            boolean exists = spaceResourceRepository.existsByName(seed.getName());
            if (exists) {
                log.info(SpaceResourceSeederLog.RESOURCE_EXISTS.getMessage(), seed.getName());
                continue;
            }

            SpaceResource resource = SpaceResource.builder()
                    .name(seed.getName())
                    .description(seed.getDescription())
                    .build();

            spaceResourceRepository.save(resource);
            log.info(SpaceResourceSeederLog.RESOURCE_CREATED.getMessage(), seed.getName());
        }

        log.info(SpaceResourceSeederLog.FINISHED.getMessage());
    }
}
