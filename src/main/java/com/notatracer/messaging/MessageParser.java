package com.notatracer.messaging;

import java.nio.ByteBuffer;

/**
 * Created by grudkowm on 2/2/19.
 */
public interface MessageParser {

    public abstract void parse(ByteBuffer buf, Listener l);

}
