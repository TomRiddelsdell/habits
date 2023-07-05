package com.habits.track.projections;

import java.util.Set;

import com.habits.track.domain.Event;
import com.habits.track.domain.EventListener;
import com.habits.track.domain.HabitCreated;

public record HabitsByUserProjection(Set<Class<?>> listensTo, HabitsByUserRepository repo) implements EventListener {

    @Override
    public void onEvent(Event<?> event) {
        if(event.data() instanceof HabitCreated) {
            HabitCreated e = (HabitCreated) event.data();
            repo().addHabitForUser(e.habitId(), e.name(), e.description(), e.habitTrackingUserId());
        }
    }
    
}
