package cue.edu.co.sqs.listener.strategy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import cue.edu.co.model.common.Event;
import cue.edu.co.model.spacereservation.commands.ReserveSpaceCommand;
import cue.edu.co.sqs.listener.constant.EventType;
import cue.edu.co.sqs.listener.payloads.RecurrentEventCreatedPayload;
import cue.edu.co.sqs.listener.payloads.SingleEventCreatedPayload;
import cue.edu.co.sqs.listener.strategy.EventHandler;
import cue.edu.co.usecase.spacereservation.ReserveSpaceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurrentEventCreatedEventHandler implements EventHandler {
    private final ReserveSpaceUseCase reserveSpaceUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public String getSupportedType() {
        return EventType.RECURRENT_EVENT_CREATED.getType();
    }

    @Override
    public void handle(Event event) {
        RecurrentEventCreatedPayload recurrentEventCreatedPayload = objectMapper
                .convertValue(event.getPayload(), RecurrentEventCreatedPayload.class);

        List<SingleEventCreatedPayload> events = recurrentEventCreatedPayload
                .events()
                .stream()
                .filter(e -> e.spaceId() != null)
                .toList();

        for(SingleEventCreatedPayload eventCreated : events) {
            reserveSpaceUseCase.execute(
                    new ReserveSpaceCommand(
                            eventCreated.spaceId(),
                            eventCreated.eventId(),
                            eventCreated.date(),
                            eventCreated.startTime(),
                            eventCreated.endTime()
                    )
            );
        }
    }
}
