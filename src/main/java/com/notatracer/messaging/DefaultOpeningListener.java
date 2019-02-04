package com.notatracer.messaging;

/**
 *
 */
public class DefaultOpeningListener implements OpeningListener {
    @Override
    public void onUndMessage(UndMessage undMessage) {
        System.out.println("Heard on UndMessage[" + undMessage + "]");
    }

    @Override
    public void onExchMessage(ExchMessage exchMessage) {
        System.out.println("Heard on ExchMessage[" + exchMessage + "]");
    }
}
