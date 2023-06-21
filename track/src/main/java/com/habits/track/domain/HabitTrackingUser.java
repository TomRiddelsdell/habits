package com.habits.track.domain;

import java.util.List;

public record HabitTrackingUser(UserId id, List<HabitId> habits) {
    
    public boolean isAuthorizedToRead(HabitId habit){
        return habits.contains(habit);
    }
}
