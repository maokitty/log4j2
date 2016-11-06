package log4j2.com.controller;

import log4j2.com.foo.Bar;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Import log4j classes.

public class MyApp {

    public static void main(final String... args) {
        final Level VERBOSE = Level.forName("VERBOSE", 550);
        final Logger logger = LogManager.getLogger();
        logger.log(VERBOSE, "a verbose message");
    }
}
