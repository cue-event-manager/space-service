package cue.edu.co.sqs.listener.payloads;

import java.util.List;

public record RecurrentEventCreatedPayload(
    List<SingleEventCreatedPayload> events
) {
}
