package com.notatracer.messaging;

import java.nio.ByteBuffer;

/**
 * Parses fully framed raw opening messages.
 */
public class OpeningMessageParser implements MessageParser {

    private static UndMessage undMessage = new UndMessage();
    private static ExchMessage exchMessage = new ExchMessage();

    @Override
    public void parse(ByteBuffer buf, Listener l) {
        OpeningListener ol = (OpeningListener)l;
        byte msgType = (byte) buf.get();

        switch(msgType) {
            case (byte)'U':
                System.out.println("Heard UndMessage");
                undMessage.clear();
                undMessage.setBuf(buf);
                ol.onUndMessage(undMessage);

                break;
            case (byte)'E':
                System.out.println("Heard ExchMessage");
                exchMessage.clear();
                exchMessage.setBuf(buf);
                ol.onExchMessage(exchMessage);

                break;
        }
    }
}
