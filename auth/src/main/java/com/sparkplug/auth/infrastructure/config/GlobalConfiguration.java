package com.sparkplug.auth.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-auth.properties")
public class GlobalConfiguration {
}
