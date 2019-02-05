package com.notatracer.demo;

import com.notatracer.messaging.ExchMessage;
import com.notatracer.messaging.Message;
import com.notatracer.messaging.UndMessage;
import com.notatracer.messaging.util.MessagingUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Focuses on various Stream API methods in generating a simple stream.
 */
public class StreamProducerDemo {

    public static UndMessage undMessage = new UndMessage();
    public static ExchMessage exchMessage = new ExchMessage();
    public static Random rand = new Random();

    public static void main(String[] args) {
        StreamProducerDemo demo = new StreamProducerDemo();
//        demo.streamGeneratedWSupplier();
//        demo.generateAndFilter();
//        demo.cantReopenPreviouslyProcessedStream();
//        demo.mapToDomain();
        demo.mapAndCollect();
    }

    private void streamGeneratedWSupplier() {
        Supplier<UndMessage> undMessageSupplier = () -> MessagingUtil.generateRandomUndMessage(undMessage);

        Stream<UndMessage> streamGenerated =
                Stream.generate(undMessageSupplier);

        List<UndMessage> undMessages =
                streamGenerated.limit(10).collect(Collectors.toList());

        System.out.println(undMessages);
    }

    private void cantReopenPreviouslyProcessedStream() {
        Supplier<UndMessage> undMessageSupplier = () -> MessagingUtil.generateRandomUndMessage(undMessage);

        Stream<UndMessage> streamGenerated =
                Stream.generate(undMessageSupplier);

        List<UndMessage> undMessages =
                streamGenerated.limit(10).collect(Collectors.toList());

        System.out.println(undMessages);

        try {
            streamGenerated.limit(10).collect(Collectors.toList());
        } catch (RuntimeException re) {
            System.out.println("Caught runtime exception trying to reopen an already processed stream.");
        }

    }

    private void generateAndFilter() {
        Supplier<? extends Message> messageSupplier = () -> {
            if (rand.nextInt() % 2 == 0) {
                return MessagingUtil.generateRandomUndMessage(undMessage);
            } else {
                return MessagingUtil.generateRandomExchMessage(exchMessage);
            }
        };

        Stream<? extends Message> streamGenerated =
                Stream.generate(messageSupplier);

        List<? extends Message> undMessages =
                streamGenerated.filter(t -> t instanceof UndMessage).
                        limit(10).collect(Collectors.toList());

        System.out.println(undMessages);

        streamGenerated =
                Stream.generate(messageSupplier);

        List<? extends Message> exchMessages =
                streamGenerated.filter(t -> t instanceof ExchMessage).
                        limit(10).collect(Collectors.toList());

        System.out.println(exchMessages);

    }

    abstract class Info {
        protected final LocalDateTime eventTime;
        protected final String marketName;
        protected final float percentOptionsOpen;

        public Info(long epochSeconds, byte marketId,int totOptions, int totOptionsNotOpen) {
            Instant instant = Instant.ofEpochSecond(epochSeconds);
            this.eventTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            switch(marketId) {
                case (byte)'A':
                    this.marketName = "ISE";
                    break;
                case (byte)'B':
                    this.marketName = "Nasdaq";
                    break;
                case (byte)'C':
                    this.marketName = "NYSE";
                    break;
                default:
                    this.marketName = "?";
                    break;
            }
            this.percentOptionsOpen = totOptions == 0 ? 0 : ((float)(totOptions - totOptionsNotOpen) / totOptions) * 100;

        }

    }
    class ExchangeInfo extends Info {
        protected final char exchangeState;

        public ExchangeInfo(long epochSeconds, byte marketId, int totOptions, int totOptionsNotOpen, byte exchangeState) {
            super(epochSeconds, marketId, totOptions, totOptionsNotOpen);
            this.exchangeState = (char)exchangeState;
        }

        @Override
        public String toString() {
            return "ExchangeInfo{" +
                    "eventTime=" + eventTime +
                    ", marketName='" + marketName + '\'' +
                    ", percentOptionsOpen=" + percentOptionsOpen +
                    ", exchangeState=" + exchangeState +
                    '}';
        }
    }

    class UndInfo extends Info {
        protected final String underlyingName;
        protected final char undState;

        public UndInfo(long epochSeconds, byte marketId, int totOptions, int totOptionsNotOpen, byte[] underlyingName, byte undState) {
            super(epochSeconds, marketId, totOptions, totOptionsNotOpen);

            this.underlyingName = new String(underlyingName);
            this.undState = (char)undState;
        }

        @Override
        public String toString() {
            return "UndInfo{" +
                    "eventTime=" + eventTime +
                    ", marketName='" + marketName + '\'' +
                    ", percentOptionsOpen=" + percentOptionsOpen +
                    ", underlyingName='" + underlyingName + '\'' +
                    ", undState=" + undState +
                    '}';
        }
    }

    private void mapToDomain() {



        Supplier<? extends Message> messageSupplier = () -> {
            if (rand.nextInt() % 2 == 0) {
                return MessagingUtil.generateRandomUndMessage(undMessage);
            } else {
                return MessagingUtil.generateRandomExchMessage(exchMessage);
            }
        };

        Stream<? extends Message> streamGenerated =
                Stream.generate(messageSupplier);

//        List<Object> info = streamGenerated
        streamGenerated
                .map(e -> e instanceof UndMessage ? new UndInfo(((UndMessage) e).timestamp, ((UndMessage) e).exchangeCode, ((UndMessage) e).totalOptions, ((UndMessage) e).totalOptionsNotOpen, ((UndMessage) e).undName, ((UndMessage) e).undState) : e)
                .map(e -> e instanceof ExchMessage ? new ExchangeInfo(((ExchMessage) e).timestamp, ((ExchMessage) e).marketId, ((ExchMessage) e).totOptions, ((ExchMessage) e).totOptionsNotOpen, ((ExchMessage) e).exchState) : e)
                .limit(100).forEach((i) -> System.out.println(i));

//                        collect(Collectors.toList());

//        System.out.println(info);

    }

    private void mapAndCollect() {
        Supplier<? extends Message> messageSupplier = () -> {
            if (rand.nextInt() % 4 == 0) {
                return MessagingUtil.generateRandomUndMessage(undMessage);
            } else {
                return MessagingUtil.generateRandomExchMessage(exchMessage);
            }
        };

        Stream<? extends Message> streamGenerated =
                Stream.generate(messageSupplier);

        List<Object> info = streamGenerated
                .peek(raw -> System.out.println(String.format("Raw message [%s]", raw) ))
                .map(e -> e instanceof UndMessage ? new UndInfo(((UndMessage) e).timestamp, ((UndMessage) e).exchangeCode, ((UndMessage) e).totalOptions, ((UndMessage) e).totalOptionsNotOpen, ((UndMessage) e).undName, ((UndMessage) e).undState) : e)
                .map(e -> e instanceof ExchMessage ? new ExchangeInfo(((ExchMessage) e).timestamp, ((ExchMessage) e).marketId, ((ExchMessage) e).totOptions, ((ExchMessage) e).totOptionsNotOpen, ((ExchMessage) e).exchState) : e)
                .limit(100)
                .peek(mapped -> System.out.println(String.format("Mapped message [%s]", mapped)))
                .collect(Collectors.toList());

        info.forEach((i) -> {
            System.out.println(i);
        });
    }



}
