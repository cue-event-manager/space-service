package cue.edu.co.sqs.listener.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SQSProcessorLog {
    EVENT_RECEIVED("Received event from SQS: {}"),
    ERROR_PROCESSING_MESSAGE("Error processing message {}");
    private final String message;
}
