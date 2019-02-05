package com.notatracer.messaging.util;

import com.notatracer.messaging.ExchMessage;
import com.notatracer.messaging.UndMessage;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.notatracer.demo.SimpleStreamSedesDemo.*;

/**
 * Created by grudkowm on 2/4/19.
 */
public class MessagingUtil {
    public static List<String> underlyings = Arrays.asList("AAPL", "BUD", "CAT", "F", "GE", "LLL", "MU", "ORCL", "TSLA", "V", "XOM");
    public static List<Byte> exchangeCodes = Arrays.asList((byte)'A', (byte)'B', (byte)'C');
    public static List<Byte> undState = Arrays.asList((byte)'C', (byte)'H', (byte)'O');
    public static List<Byte> exchState = Arrays.asList((byte)'C', (byte)'H', (byte)'O');
    public static int MAX_OPTIONS = 1000;
    public static int MAX_UNDERLYINGS = 500;
    public static Random rand = new Random();


    public static ExchMessage generateRandomExchMessage(ExchMessage exchMessage) {
        // total options is random between 1 and N
        // total options not open is a random number between total options and 0

        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(-5));
        int totalOptions = rand.nextInt(MAX_OPTIONS);
        int totalOptionsNotOpen = totalOptions == 0 ? 0 : rand.nextInt(totalOptions);
        byte exchangeCode = exchangeCodes.get(rand.nextInt(exchangeCodes.size()));
        byte eState = exchState.get(rand.nextInt(exchState.size()));
        int totUndsNotOpen = rand.nextInt(MAX_UNDERLYINGS);

        exchMessage.timestamp = timestamp;
        exchMessage.marketId = exchangeCode;
        exchMessage.exchState = eState;
        exchMessage.totOptions = totalOptions;
        exchMessage.totOptionsNotOpen = totalOptionsNotOpen;
        exchMessage.totUndsNotOpen = totUndsNotOpen;

        return exchMessage;
    }


    public static UndMessage generateRandomUndMessage(UndMessage undMessage) {

        // total options is random between 1 and N
        // total options not open is a random number between total options and 0

        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(-5));
        int MAX_OPTIONS = 1000;
        int totalOptions = rand.nextInt(MAX_OPTIONS);
        int totalOptionsNotOpen = totalOptions == 0 ? 0 : rand.nextInt(totalOptions);
        byte exchangeCode = exchangeCodes.get(rand.nextInt(exchangeCodes.size()));
        byte uState = undState.get(rand.nextInt(undState.size()));

        undMessage.timestamp = timestamp;
        undMessage.exchangeCode = exchangeCode;
        String und = underlyings.get(rand.nextInt(underlyings.size()));
        System.arraycopy(und.getBytes(), 0, undMessage.undName, 0, und.length());
        undMessage.undState = uState;
        undMessage.totalOptions = totalOptions;
        undMessage.totalOptionsNotOpen = totalOptionsNotOpen;

        return undMessage;
    }

}
