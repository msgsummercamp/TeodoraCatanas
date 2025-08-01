package org.apache.maven.archetypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger log = LoggerFactory.getLogger( App.class );

    public static void main(String[] args) {
        log.info("Environment: {}", System.getProperty("env"));
    }
}
