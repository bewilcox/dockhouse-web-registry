package org.dockhouse.dockerregistry.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BWI on 11/10/2014.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.dockhouse.dockerregistry"})
public class ApplicationConfig {
}
