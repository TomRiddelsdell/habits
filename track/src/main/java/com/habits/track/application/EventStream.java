package com.habits.track.application;

import java.util.stream.Stream;

import com.habits.track.domain.Event;
import com.habits.track.domain.EventStreamVersion;

public record EventStream(Stream<? extends Event<?>> events, EventStreamVersion version) {

    public EventStream addEvents(Stream<? extends Event<?>> events){
        return new EventStream(Stream.concat(events(), events), this.version());
    }
}
