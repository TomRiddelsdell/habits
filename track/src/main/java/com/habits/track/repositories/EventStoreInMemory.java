package com.habits.track.repositories;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.habits.track.application.EventStream;
import com.habits.track.domain.Event;
import com.habits.track.domain.EventStore;
import com.habits.track.domain.EventStreamId;
import com.habits.track.domain.EventStreamVersion;
import com.habits.track.domain.EventListener;

public class EventStoreInMemory implements EventStore {

    Map<EventStreamId,EventStreamSupplier> eventStreams = new HashMap<>();
    Map<Class<?>,Set<EventListener>> eventListenersByEventType = new HashMap<>();

    @Override
    public Optional<EventStream> load(EventStreamId id){

        var stream = eventStreams.get(id);
        if(stream == null)
            return Optional.empty();

        return Optional.of(stream.get());
    }

    @Override
    public void create(Stream<? extends Event<?>> events, EventStreamId id) {
        if(eventStreams.containsKey(id))
            throw new IllegalArgumentException("Event stream already exists");

        var asList = events.collect(Collectors.toList());

        if(asList.isEmpty())
            throw new IllegalArgumentException("Cannot add an empty stream");

        eventStreams.put(id, new EventStreamSupplier(asList, EventStreamVersion.initialVersion()));

        // Trigger the event listeners
        for (Event<?> event : asList) {
            for (EventListener listener : eventListenersByEventType.getOrDefault(event.data().getClass(), new HashSet<>())) {
               listener.onEvent(event); 
            }
        }
    }

    @Override
    public void append(Stream<? extends Event<?>> events, EventStreamId id, EventStreamVersion version) {

        var stored = eventStreams.get(id);
        if(stored == null)
            throw new IllegalArgumentException("Stream ID doesn't exist");

        if(!stored.version().equals(version))
            throw new IllegalArgumentException("Stale event stream version");

        var asList = stored.get().addEvents(events).events().collect(Collectors.toList());

        eventStreams.put(id, new EventStreamSupplier(asList, new EventStreamVersion(version.version()+1)));

        // Trigger the event listeners
        for (Event<?> event : asList) {
            for (EventListener listener : eventListenersByEventType.getOrDefault(event.getClass(), new HashSet<>())) {
               listener.onEvent(event); 
            }
        }
    }

    @Override
    public void subscribe(EventListener listener){
        for (Class<?> c : listener.listensTo()) {
            if(eventListenersByEventType.containsKey(c))
                eventListenersByEventType.get(c).add(listener);
            else 
                eventListenersByEventType.put(c, new HashSet<>(){{add(listener);}}); 
        }
    }
}
