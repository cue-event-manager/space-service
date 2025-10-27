package cue.edu.co.sqs.listener.strategy;

import cue.edu.co.model.common.Event;

public interface EventHandler {
    String getSupportedType();
    void handle(Event event);
}
