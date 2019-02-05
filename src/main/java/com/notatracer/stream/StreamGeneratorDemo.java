package com.notatracer.stream;

import com.notatracer.connection.Connection;
import com.notatracer.connection.ConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

/**
 *
 */
@SpringBootApplication
public class StreamGeneratorDemo implements ApplicationListener<ApplicationReadyEvent> {

    private static Logger LOGGER = Logger.getLogger(StreamGeneratorDemo.class);

    @Autowired
    private ConnectionFactory connectionFactory;

    public static void main(String[] args) {
        LOGGER.info("Main...");
        ConfigurableApplicationContext ctx = SpringApplication.run(StreamGeneratorDemo.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.info("onApplicationEvent...");
        Optional<Connection> connectionLookup = connectionFactory.get("dummy");

        if (!connectionLookup.isPresent())  {
            throw new IllegalArgumentException("Unknown protocol");
        } else {
            Connection connection = connectionLookup.get();
//            connection.

        }
//		startConnections();
//        startMockConnections();
    }
}
