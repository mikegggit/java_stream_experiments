package com.notatracer.connection;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by grudkowm on 2/2/19.
 */
@Component
public interface ConnectionFactory {

    Optional<Connection> get(String protocol);
}
