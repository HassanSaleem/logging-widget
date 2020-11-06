package com.assetcontrol.loggingwidget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(LoggingThread.class);

    @Override
    public void run() {
        while (true){
            logger.info("some info message");
            logger.info("some other info message");
            logger.warn("some warn message");
            logger.warn("some other warn message");
            logger.error("some error message");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
