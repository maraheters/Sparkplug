package com.sparkplug.catalog.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayCatalogConfig {

    @Bean
    public FluentConfiguration flywayCatalogConfiguration(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .schemas("catalog")
                .table("flyway_history_catalog")
                .locations("classpath:db/migration/catalog");
    }
}
