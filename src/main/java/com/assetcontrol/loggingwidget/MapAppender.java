package com.assetcontrol.loggingwidget;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.assetcontrol.loggingwidget.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static java.util.logging.Level.*;

public class MapAppender extends AppenderBase<ILoggingEvent> {


    ConcurrentLinkedQueue<ILoggingEvent> deque = new ConcurrentLinkedQueue<ILoggingEvent>();
    private static final MapAppender instance = new MapAppender();

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        List<ILoggingEvent> logs = instance.deque.stream().filter(log -> {
           return log.getFormattedMessage().equals(iLoggingEvent.getFormattedMessage())
                    && log.getLevel().equals(iLoggingEvent.getLevel());
        }).collect(Collectors.toList());
        if (logs.isEmpty()) {
            instance.deque.add(iLoggingEvent);
        }
    }

    public static List<LogModel> getLogs(){
        return instance.getRecentLogs();
    }

    private List<LogModel> getRecentLogs(){
        ArrayList<ILoggingEvent> logs = new ArrayList<>();
        Boolean flag = true;

        while (flag){
            if (instance.deque.isEmpty()){
                flag = false;
                continue;
            }
            ILoggingEvent event = instance.deque.remove();

            long timestamp = event.getTimeStamp();
            long currentTimestamp = System.currentTimeMillis();
            if (currentTimestamp - timestamp > 5000){
                continue;
            }
            logs.add(event);
        }

        ArrayList<LogModel> logCount = new ArrayList<>();

        LogModel info = new LogModel();
        info.setLevel("INFO");
        info.setCount(logs.stream().filter(log -> log.getLevel().equals(Level.INFO)).count());
        logCount.add(info);

        LogModel warnings = new LogModel();
        warnings.setLevel("WARN");
        warnings.setCount(logs.stream().filter(log -> log.getLevel().equals(Level.WARN)).count());
        logCount.add(warnings);

        LogModel errors = new LogModel();
        errors.setLevel("ERROR");
        errors.setCount(logs.stream().filter(log -> log.getLevel().equals(Level.ERROR)).count());
        logCount.add(errors);

        return logCount;
    }
}
