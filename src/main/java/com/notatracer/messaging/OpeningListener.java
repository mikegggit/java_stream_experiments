package com.notatracer.messaging;

/**
 *
 */
public interface OpeningListener extends Listener {

    public void onUndMessage(UndMessage undMessage);
    public void onExchMessage(ExchMessage exchMessage);

}
