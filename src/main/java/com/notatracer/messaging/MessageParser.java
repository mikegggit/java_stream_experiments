package com.notatracer.messaging;

import java.nio.ByteBuffer;

/**
 *
 */
public interface MessageParser {

    public abstract void parse(ByteBuffer buf, Listener l);

}
