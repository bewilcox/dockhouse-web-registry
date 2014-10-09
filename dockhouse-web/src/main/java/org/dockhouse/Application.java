package org.dockhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by BWI on 01/10/2014.
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

    private static Class<Application> entryPointClass = Application.class;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(entryPointClass);
        app.setShowBanner(false);
        app.run(args);
    }

}
