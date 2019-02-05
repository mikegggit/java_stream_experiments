package com.notatracer.demo;

import com.notatracer.messaging.*;
import com.notatracer.messaging.util.MessagingUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.notatracer.messaging.util.MessagingUtil.underlyings;
import static org.apache.coyote.http11.Constants.a;

/**
 * Demonstrates creation of a simple non-realistic stream consisting of
 * two message types.
 * 
 * Stream is serialized to disk, loaded, and replayed.
 *
 * Interesting notes:
 * - We can't stream in a binary encoded file where messages are delimited by message
 * length.
 *
 */
public class SimpleStreamSedesDemo {

    public static UndMessage undMessage = new UndMessage();
    public static ExchMessage exchMessage = new ExchMessage();

    public SimpleStreamSedesDemo() {}

    public static void main(String[] args) throws IOException {
        SimpleStreamSedesDemo demo = new SimpleStreamSedesDemo();
        
        int numMessages=500;
//        demo.sedesSimpleStream(numMessages);
//        demo.sedesMultipleMessagesStream(numMessages);
//        demo.sedesSkipUndMessages();
//        demo.stream100RandomUnds();
//        demo.stream100RandomUndMessages();
        demo.streamGeneratedWSupplier();

    }

    private void streamGeneratedWSupplier() {
        Supplier<UndMessage> undMessageSupplier = () -> MessagingUtil.generateRandomUndMessage(undMessage);

        Stream<UndMessage> streamGenerated =
                Stream.generate(undMessageSupplier);

        List<UndMessage> undMessages =
                streamGenerated.limit(10).collect(Collectors.toList());

        System.out.println(undMessages.size());
        System.out.println(undMessages);
    }

    private void sedesSimpleStream(int numMessages) {

        ByteBuffer header = ByteBuffer.allocate(4);
        ByteBuffer bb = ByteBuffer.allocate(1000);

        undMessage.clear();

        File out = new File("/tmp/undStream.out");
        boolean append = false;
        try (FileChannel channel = new FileOutputStream(out, append).getChannel()) {
            IntStream.range(0, numMessages).forEach( i -> {
                        undMessage.clear();
                        bb.clear();
                        header.clear();
                        UndMessage message = MessagingUtil.generateRandomUndMessage(undMessage);
                        System.out.println(message);
                        message.encode(bb);
                        bb.flip();
                        try {
                            ByteBuffer slice = bb.slice();
                            header.putInt(slice.remaining());
                            header.flip();

                            channel.write(header);
                            channel.write(slice);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File in = new File("/tmp/undStream.out");
        bb.clear();

        try (FileInputStream fis = new FileInputStream(in)) {
            try (FileChannel channel = fis.getChannel()) {
                int i = 0;

                    int bytesRead = channel.read(bb);
                    if (bytesRead <= 0) {
                        return;
                    }
                    bb.flip(); // prepare for reading


                    MessageParser parser = new OpeningMessageParser();
                    OpeningListener l = new DefaultOpeningListener();

                    // reset position to 0, limit to amount read
                    while (bb.remaining() >= 4) {
                        int len = bb.getInt();


                        if (len > 0 && bb.remaining() >= len) {
                            byte msgType = (byte)bb.get();
                            undMessage.parse(bb);
                            System.out.println(undMessage);
                        } else {
                            bb.compact();
                            int position = bb.position();
                            channel.read(bb);
                            bb.flip();
                            byte msgType = (byte)bb.get();
                            undMessage.parse(bb);
                            System.out.println(undMessage);
                        }
                    }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sedesMultipleMessagesStream(int numMessages) {
        ByteBuffer header = ByteBuffer.allocate(4);
        ByteBuffer bb = ByteBuffer.allocate(10000);

        undMessage.clear();

        File out = new File("/tmp/mixedStream.out");
        boolean append = false;
        try (FileChannel channel = new FileOutputStream(out, append).getChannel()) {
            IntStream.range(0, numMessages).forEach( i -> {

                Message message = null;
                if ( i % 10 == 0 ) {
                    exchMessage.clear();
                    bb.clear();
                    header.clear();
                    message = MessagingUtil.generateRandomExchMessage(exchMessage);
                } else {
                    undMessage.clear();
                    bb.clear();
                    header.clear();
                    message = MessagingUtil.generateRandomUndMessage(undMessage);
                }
                System.out.println(message);
                message.encode(bb);
                bb.flip();
                try {
                    ByteBuffer slice = bb.slice();
                    header.putInt(slice.remaining());
                    header.flip();

                    channel.write(header);
                    channel.write(slice);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File in = new File("/tmp/mixedStream.out");
        bb.clear();

        try (FileInputStream fis = new FileInputStream(in)) {
            try (FileChannel channel = fis.getChannel()) {
                int i = 0;

                int bytesRead = channel.read(bb);
                if (bytesRead <= 0) {
                    return;
                }
                bb.flip(); // prepare for reading

                // reset position to 0, limit to amount read
                while (bb.remaining() >= 4) {
                    int len = bb.getInt();


                    if (len > 0 && bb.remaining() >= len) {
                        byte msgType = (byte)bb.get();

                        System.out.println((char)msgType);
                        switch(msgType) {
                            case (byte)'U':
                                undMessage.parse(bb);
                                System.out.println(undMessage);
                                break;
                            case (byte)'E':
                                exchMessage.parse(bb);
                                System.out.println(exchMessage);
                                break;
                        }
                    } else {
                        bb.compact();
                        channel.read(bb);
                        bb.flip();

                        byte msgType = (byte)bb.get();

                        switch(msgType) {
                            case (byte)'U':
                                undMessage.parse(bb);
                                System.out.println(undMessage);
                                break;
                            case (byte)'E':
                                exchMessage.parse(bb);
                                System.out.println(exchMessage);
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void stream100RandomUnds() {
        Random rand = new Random();

        IntStream.range(0, 100)
                .forEach( i -> {
                    String randomUnd = MessagingUtil.underlyings.get(rand.nextInt(MessagingUtil.underlyings.size()));
                    System.out.println(randomUnd);
                });
    }

    private void stream100RandomUndMessages() {
        IntStream.range(0, 100)
                .forEach( i -> {
                    System.out.println(i);
                    System.out.println(MessagingUtil.generateRandomUndMessage(undMessage));
                });
    }


}
