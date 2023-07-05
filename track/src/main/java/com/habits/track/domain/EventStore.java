package com.habits.track.domain;

import java.util.Optional;
import java.util.stream.Stream;

import com.habits.track.application.EventStream;

public interface EventStore {

    /**
     * Load an event stream from the store
     * @param id the id of the strea to load
     * @return the event stream if it exists otherwise Optional.empty()
     */
    public Optional<EventStream> load(EventStreamId id);

    /**
     * This creates a new EventStream for an aggregate
     * @param events the initial set of events to store in the stream
     * @param id the id of the stream
     */
    public void create(Stream<? extends Event<?>> events, EventStreamId id);

    /**
     * This appends a set of events to an existing stream and publishes those events
     * to any subscribed EventListeners
     * @param events the events to append
     * @param id the id of the stream
     * @param version the version of the stream expected to be in the store. The append will
     *        fail if the stored version doesn't match this version
     */
    public void append(Stream<? extends Event<?>> events, EventStreamId id, EventStreamVersion version);
    
    /**
     * Subscribe an EventListener to all events with a type found in listener.listenTo()
     * @param listener the listener implementing the callback onEvent()
     */
    public void subscribe(EventListener listener);
}