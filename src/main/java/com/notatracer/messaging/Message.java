package com.notatracer.messaging;

import java.nio.ByteBuffer;

/**
 * Created by grudkowm on 2/2/19.
 */
public abstract class Message {

    public static final byte EMPTY_BYTE = '?';

    protected byte messageType = EMPTY_BYTE;

    protected ByteBuffer buf;


    public enum Lengths {
        UND(5);

        int size = -1;

        Lengths(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public enum ExchangeCodes {

        NYSE((byte)'N'), Nasdaq((byte)'Q');

        byte code = EMPTY_BYTE;

        ExchangeCodes(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }
    public ByteBuffer getBuf() {
        return buf;
    }

    /**
     * Serializes fields into buf.
     */
    public abstract void encode(ByteBuffer buf);

    /**
     * Clears field values.
     */
    public abstract void clear();

    /**
     * Writes to a file.
     */
    public abstract void write();

    /**
     * Parses values out of buf into Message fields.
     */
    public abstract void parse(ByteBuffer buf);

    public void setBuf(ByteBuffer buf) {
        this.buf = buf;
    }

    public byte getMessageType() {
        return messageType;
    }
}
