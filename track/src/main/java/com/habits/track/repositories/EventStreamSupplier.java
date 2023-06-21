package com.habits.track.repositories;

import java.util.List;

import com.habits.track.application.EventStream;
import com.habits.track.domain.Event;
import com.habits.track.domain.EventStreamVersion;

public record EventStreamSupplier(List<? extends Event<?>> events, EventStreamVersion version) {

    EventStream get(){
        return new EventStream(events.stream(), version);
    }
}
