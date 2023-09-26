package com.idealize.config;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = IntegrationConfig.Initializer.class)
public class IntegrationConfig {
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer<?> mySQL = new MySQLContainer<>("mysql:8.0.28");

        public void startContainers() {
            Startables.deepStart(Stream.of(mySQL)).join();
        }

        private static Map<String, String> createConfigurationConnection() {
            return Map.of(
                    "spring.datasource.url", mySQL.getJdbcUrl(),
                    "spring.datasource.username", mySQL.getUsername(),
                    "spring.datasource.password", mySQL.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource tescontainers =
                    new MapPropertySource("testcontainers", (Map) createConfigurationConnection());

            environment.getPropertySources().addFirst(tescontainers);
        }
    }
}
