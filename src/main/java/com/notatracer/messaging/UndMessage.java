package com.notatracer.messaging;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 */
public class UndMessage extends Message {

    public UndMessage() {
        this.messageType = 'U';
    }

    public long timestamp = -1;
    public byte exchangeCode = EMPTY_BYTE;
    public byte[] undName = new byte[Lengths.UND.getSize()];
    public byte undState = EMPTY_BYTE;
    public int totalOptions = -1;
    public int totalOptionsNotOpen = -1;

    @Override
    public void encode(ByteBuffer buf) {
        buf.put(this.getMessageType());
        buf.putLong(this.timestamp);
        buf.put(exchangeCode);
        buf.put(undName);
        buf.put(undState);
        buf.putInt(totalOptions);
        buf.putInt(totalOptionsNotOpen);
    }

    @Override
    public void write() {
    }

    @Override
    public void clear() {
        this.timestamp  = -1;
        this.exchangeCode = EMPTY_BYTE;
        Arrays.fill(this.undName, (byte)' ');
        this.undState = EMPTY_BYTE;
        this.totalOptions = -1;
        this.totalOptionsNotOpen = -1;
        this.buf = null;
    }

    @Override
    public void parse(ByteBuffer buf) {
        this.timestamp = buf.getLong();
        this.exchangeCode = buf.get();
        buf.get(this.undName);
        this.undState = buf.get();
        this.totalOptions = buf.getInt();
        this.totalOptionsNotOpen = buf.getInt();
    }

    @Override
    public String toString() {
        return "UndMessage{" +
                "msgType=[" + (char)messageType +
                "], timestamp=[" + timestamp +
                "], exchangeCode=[" + (char)exchangeCode +
                "], undName=[" + new String(undName) +
                "], undState=[" + (char)undState +
                "], totalOptions=[" + totalOptions +
                "], totalOptionsNotOpen=[" + totalOptionsNotOpen +
                "]}";
    }
}
