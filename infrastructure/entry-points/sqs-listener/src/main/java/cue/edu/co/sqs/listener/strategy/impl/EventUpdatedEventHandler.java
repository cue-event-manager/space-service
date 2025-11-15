package cue.edu.co.sqs.listener.strategy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import cue.edu.co.model.common.Event;
import cue.edu.co.model.spacereservation.commands.CancelSpaceReservationCommand;
import cue.edu.co.model.spacereservation.commands.ReserveSpaceCommand;
import cue.edu.co.sqs.listener.constant.EventType;
import cue.edu.co.sqs.listener.payloads.EventUpdatedPayload;
import cue.edu.co.sqs.listener.strategy.EventHandler;
import cue.edu.co.usecase.spacereservation.CancelSpaceReservationUseCase;
import cue.edu.co.usecase.spacereservation.ReserveSpaceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventUpdatedEventHandler implements EventHandler {

    private final ReserveSpaceUseCase reserveSpaceUseCase;
    private final CancelSpaceReservationUseCase cancelSpaceReservationUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public String getSupportedType() {
        return EventType.EVENT_UPDATED.getType();
    }

    @Override
    public void handle(Event event) {
    EventUpdatedPayload payload = objectMapper.convertValue(event.getPayload(), EventUpdatedPayload.class);

        boolean affectsReservation =
                payload.spaceChanged() ||
                        payload.dateChanged() ||
                        payload.timeChanged() ||
                        payload.statusChanged();

        if (!affectsReservation) return;

        if (payload.spaceChanged() || payload.dateChanged() || payload.timeChanged()) {
            cancelSpaceReservationUseCase.execute(
                    new CancelSpaceReservationCommand(payload.eventId())
            );
        }

        reserveSpaceUseCase.execute(
                new ReserveSpaceCommand(
                        payload.spaceId(),
                        payload.eventId(),
                        payload.date(),
                        payload.startTime(),
                        payload.endTime()
                )
        );
    }
}
