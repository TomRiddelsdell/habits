package com.habits.track.application;

import java.util.Set;
import java.util.UUID;

import com.habits.track.domain.EventStore;
import com.habits.track.domain.Habit;
import com.habits.track.domain.HabitCreated;
import com.habits.track.domain.HabitId;
import com.habits.track.domain.HabitTrackingUser;
import com.habits.track.domain.UserId;
import com.habits.track.domain.services.AuthorizationDomainService;
import com.habits.track.projections.HabitsByUserRepository;
import com.habits.track.projections.HabitsByUserProjection;

public final class HabitService {
    
    private final EventStore eventStore;
    private final AuthorizationDomainService authService;
    //private final HabitDomainService habitDomainService;
    private final HabitsByUserRepository habitsByUserRepo;
    
    public HabitService(EventStore eventStore, AuthorizationDomainService userService, HabitsByUserRepository habitByUserRepo){
        this.eventStore = eventStore;
        this.authService = userService;
        //this.habitDomainService = habitDomainService;
        this.habitsByUserRepo = habitByUserRepo;

        initializeProjections();
    }

    /**
     * Registers all Projections with the EventStore
     */
    private void initializeProjections() {
        eventStore.subscribe(new HabitsByUserProjection(Set.of(HabitCreated.class), habitsByUserRepo));
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
