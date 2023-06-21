package com.habits.track.application;

import java.util.UUID;

import com.habits.track.domain.EventStore;
import com.habits.track.domain.Habit;
import com.habits.track.domain.HabitId;
import com.habits.track.domain.HabitTrackingUser;
import com.habits.track.domain.UserId;
import com.habits.track.domain.services.AuthorizationDomainService;
//import com.habits.track.domain.services.HabitDomainService;

public final class HabitService {
    
    private final EventStore eventStore;
    private final AuthorizationDomainService authService;
    //private final HabitDomainService habitDomainService;
    
    public HabitService(EventStore eventStore, AuthorizationDomainService userService/* , HabitDomainService habitDomainService*/){
        this.eventStore = eventStore;
        this.authService = userService;
        //this.habitDomainService = habitDomainService;
    }

    public void addHabit(UUID habitId, String name, String description, UUID userId){
        HabitTrackingUser user = this.authService.habitTrackingUserFrom(new UserId(userId)).get(); // This will throw a NPE if the user is not found. Is that what we want?

        Habit habit = new Habit(new Habit.HabitState());
        HabitId id = new HabitId(habitId);
        var events = habit.createHabit(id, name, description, user);
        eventStore.create(events, id.eventStreamId());
    }

    public void deleteHabit(UUID id){

    }
}
