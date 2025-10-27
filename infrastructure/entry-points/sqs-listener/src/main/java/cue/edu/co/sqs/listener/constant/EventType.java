package cue.edu.co.sqs.listener.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventType {
    SINGLE_EVENT_CREATED("SingleEventCreated");
    private final String type;
}
