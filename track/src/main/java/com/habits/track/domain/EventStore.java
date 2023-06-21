package com.habits.track.domain;

import java.util.Optional;
import java.util.stream.Stream;

import com.habits.track.application.EventStream;

public interface EventStore {
    public Optional<EventStream> load(EventStreamId id);
    public void create(Stream<? extends Event<?>> events, EventStreamId id);
    public void append(Stream<? extends Event<?>> events, EventStreamId id, EventStreamVersion version);
    public EventStreamVersion headVersion(EventStreamId id);
}