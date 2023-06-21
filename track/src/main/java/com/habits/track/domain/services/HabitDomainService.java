package com.habits.track.domain.services;

import java.util.Optional;

import com.habits.track.application.EventStream;
import com.habits.track.domain.EventStore;
import com.habits.track.domain.Habit;
import com.habits.track.domain.HabitId;
import com.habits.track.domain.HabitTrackingUser;

public final class HabitDomainService{

    private EventStore eventStore;

    public HabitDomainService(EventStore e){
        eventStore = e;
    }

    public Optional<Habit> fromId(HabitId habitId, HabitTrackingUser role) {
        if(role == null || !role.isAuthorizedToRead(habitId))
            throw new SecurityException("Not authorized to read habit");

        Optional<EventStream> events = eventStore.load(habitId.eventStreamId());
        if(events.isEmpty())
            return Optional.empty();

        Habit.HabitState state = new Habit.HabitState();
        state.applyEvents(events.get());
        return Optional.of(new Habit(state));
    }

}