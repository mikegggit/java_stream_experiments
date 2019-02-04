package com.notatracer.connection;

import java.util.Optional;

/**
 * Created by grudkowm on 2/2/19.
 */
public class ConnectionFactoryImpl implements ConnectionFactory {
    public static final String PROTOCOL_DUMMY = "dummy";

    @Override
    public Optional<Connection> get(String protocol) {
        if (protocol.equals(PROTOCOL_DUMMY))
            return Optional.of(new LocalStreamConnection());
        return Optional.empty();
    }
}
