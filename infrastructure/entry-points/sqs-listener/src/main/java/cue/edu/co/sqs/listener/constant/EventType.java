package cue.edu.co.sqs.listener.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventType {
    SINGLE_EVENT_CREATED("SingleEventCreated"),
    RECURRENT_EVENT_CREATED("RecurrentEventCreated"),
    EVENT_UPDATED("EventUpdated");

    private final String type;
}
