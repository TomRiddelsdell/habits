package com.habits.track.domain;

import java.util.UUID;

public record HabitId(UUID id) {

    public static HabitId newId(){
        return new HabitId(UUID.randomUUID());
    }

    public EventStreamId eventStreamId(){
        return new EventStreamId(id);
    }
}