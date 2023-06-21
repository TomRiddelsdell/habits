package com.habits.track.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.habits.track.application.EventStream;
import com.habits.track.domain.Event;
import com.habits.track.domain.EventStore;
import com.habits.track.domain.EventStreamId;
import com.habits.track.domain.EventStreamVersion;

public class EventStoreInMemory implements EventStore {

    Map<EventStreamId,EventStreamSupplier> eventStreams = new HashMap<>();

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
    }

    @Override
    public void append(Stream<? extends Event<?>> events, EventStreamId id, EventStreamVersion version) {

        var stored = eventStreams.get(id);
        if(stored == null)
            throw new IllegalArgumentException("Stream ID doesn't exist");

        if(!stored.version().equals(version))
            throw new IllegalArgumentException("Stale event stream version");

        eventStreams.put(id, new EventStreamSupplier(stored.get().addEvents(events).events().collect(Collectors.toList()), new EventStreamVersion(version.version()+1)));
    }

    @Override
    public EventStreamVersion headVersion(EventStreamId id){
        return new EventStreamVersion(1);
    }
}
