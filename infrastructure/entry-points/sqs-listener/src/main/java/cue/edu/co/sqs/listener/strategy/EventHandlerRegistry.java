package cue.edu.co.sqs.listener.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventHandlerRegistry {

    private final List<EventHandler> handlers;

    public EventHandler getHandlerFor(String type) {
        return handlers.stream()
                .filter(h -> h.getSupportedType().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No handler found for event type: " + type));
    }
}