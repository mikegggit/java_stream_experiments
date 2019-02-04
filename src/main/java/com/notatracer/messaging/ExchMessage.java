package com.notatracer.messaging;

import java.nio.ByteBuffer;

/**
 *
 */
public class ExchMessage extends Message {

    public ExchMessage() {
        this.messageType = 'E';
    }

    public long timestamp = -1;
    public byte marketId= EMPTY_BYTE;
    public int totOptions = -1;
    public int totOptionsNotOpen = -1;
    public int totUndsNotOpen = -1;
    public byte exchState = EMPTY_BYTE;

    @Override
    public void encode(ByteBuffer buf) {
        buf.put(this.getMessageType());
        buf.putLong(this.timestamp);
        buf.put(marketId);
        buf.putInt(totOptions);
        buf.putInt(totOptionsNotOpen);
        buf.putInt(totUndsNotOpen);
        buf.put(exchState);
    }

    @Override
    public void write() {

    }

    @Override
    public void clear() {
        this.timestamp = -1;
        this.marketId= EMPTY_BYTE;
        this.totOptions = -1;
        this.totOptionsNotOpen = -1;
        this.totUndsNotOpen = -1;
        this.exchState = EMPTY_BYTE;
        this.buf = null;
    }

    @Override
    public void parse(ByteBuffer buf) {
        this.timestamp = buf.getLong();
        this.marketId = buf.get();
        this.totOptions = buf.getInt();
        this.totOptionsNotOpen = buf.getInt();
        this.totUndsNotOpen = buf.getInt();
        this.exchState = buf.get();
    }

    @Override
    public String toString() {
        return "ExchMessage{" +
                "msgType=[" + (char)messageType +
                "], timestamp=[" + timestamp +
                "], marketId=[" + (char)marketId +
                "], totOptions=[" + totOptions +
                "], totOptionsNotOpen=[" + totOptionsNotOpen +
                "], totUndsNotOpen=[" + totUndsNotOpen +
                "], exchState=[" + (char)exchState +
                "]}";
    }
}
