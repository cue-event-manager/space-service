package cue.edu.co.sqs.listener.strategy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import cue.edu.co.model.common.Event;
import cue.edu.co.model.spacereservation.commands.ReserveSpaceCommand;
import cue.edu.co.sqs.listener.constant.EventType;
import cue.edu.co.sqs.listener.payloads.SingleEventCreatedPayload;
import cue.edu.co.sqs.listener.strategy.EventHandler;
import cue.edu.co.usecase.spacereservation.ReserveSpaceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SingleEventCreatedEventHandler implements EventHandler {
    private final ReserveSpaceUseCase reserveSpaceUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public String getSupportedType() {
        return EventType.SINGLE_EVENT_CREATED.getType();
    }

    @Override
    public void handle(Event event) {
        SingleEventCreatedPayload singleEventCreatedPayload = objectMapper
                .convertValue(event.getPayload(), SingleEventCreatedPayload.class);

        reserveSpaceUseCase.execute(
                new ReserveSpaceCommand(
                        singleEventCreatedPayload.spaceId(),
                        singleEventCreatedPayload.eventId(),
                        singleEventCreatedPayload.date(),
                        singleEventCreatedPayload.startTime(),
                        singleEventCreatedPayload.endTime()
                )
        );
    }
}
