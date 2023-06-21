package com.habits.track.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HabitTest {
    
    @Test
    void constructHabit(){
        var habit = new Habit.HabitState().name("myName").description("myDescription").build();
        assertEquals("myDescription", habit.description());
        assertEquals("myName", habit.name());
    }
}
