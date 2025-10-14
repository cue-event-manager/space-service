package cue.edu.co.config;

import cue.edu.co.model.campus.gateways.CampusRepository;
import cue.edu.co.model.space.gateways.SpaceRepository;
import cue.edu.co.model.spacetype.gateways.SpaceTypeRepository;
import cue.edu.co.model.spaceresource.gateways.SpaceResourceRepository;
import cue.edu.co.model.spacestatus.gateways.SpaceStatusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import({UseCasesConfig.class, SpaceTypeUseCaseConfig.class, SpaceResourceUseCaseConfig.class, SpaceStatusUseCaseConfig.class, SpaceUseCaseConfig.class})
    static class TestConfig {

        @Bean
        public SpaceTypeRepository spaceTypeRepository() {
            return mock(SpaceTypeRepository.class);
        }

        @Bean
        public SpaceResourceRepository spaceResourceRepository() {
            return mock(SpaceResourceRepository.class);
        }

        @Bean
        public SpaceStatusRepository spaceStatusRepository() {
            return mock(SpaceStatusRepository.class);
        }

        @Bean
        public SpaceRepository spaceRepository() {
            return mock(SpaceRepository.class);
        }

        @Bean
        public CampusRepository campusRepository() {
            return mock(CampusRepository.class);
        }

        @Bean
        public MyUseCase myUseCase() {
            return new MyUseCase();
        }
    }

    static class MyUseCase {
        public String execute() {
            return "MyUseCase Test";
        }
    }
}