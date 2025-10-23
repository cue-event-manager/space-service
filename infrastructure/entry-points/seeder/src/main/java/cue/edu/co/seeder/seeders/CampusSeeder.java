package cue.edu.co.seeder.seeders;

import cue.edu.co.model.campus.Campus;
import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.seeder.constants.CampusSeedConstant;
import cue.edu.co.seeder.constants.CampusSeederLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class CampusSeeder implements CommandLineRunner {

    private final CampusRepository campusRepository;

    @Override
    public void run(String... args) {
        log.info(CampusSeederLog.STARTING.getMessage());

        for (CampusSeedConstant seed : CampusSeedConstant.values()) {
            campusRepository.findByName(seed.getName())
                    .ifPresentOrElse(
                            c -> log.info(CampusSeederLog.CAMPUS_FOUND.getMessage(), seed.getName()),
                            () -> {
                                Campus campus = Campus.builder()
                                        .name(seed.getName())
                                        .address(seed.getAddress())
                                        .build();

                                campusRepository.save(campus);
                                log.info(CampusSeederLog.CAMPUS_CREATED.getMessage(), seed.getName());
                            }
                    );
        }

        log.info(CampusSeederLog.FINISHED.getMessage());
    }
}