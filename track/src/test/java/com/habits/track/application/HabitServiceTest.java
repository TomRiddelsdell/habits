package com.habits.track.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.habits.track.domain.Habit;
import com.habits.track.domain.HabitId;
import com.habits.track.domain.HabitTrackingUser;
import com.habits.track.domain.UserId;
import com.habits.track.domain.services.AuthorizationDomainService;
import com.habits.track.domain.services.HabitDomainService;
import com.habits.track.repositories.EventStoreInMemory;

@ExtendWith(MockitoExtension.class)
public class HabitServiceTest {
 
    HabitService habitService;
    HabitDomainService habitDomainService;
    AuthorizationDomainService authService;
    UserId userId;

    @BeforeEach
    void init(@Mock AuthorizationDomainService userService){
        userId = new UserId(UUID.randomUUID());
        this.authService = userService;
        var eventStore = new EventStoreInMemory();
        habitDomainService = new HabitDomainService(eventStore);
        habitService = new HabitService(eventStore, userService);
    }

    @Test
    void testHabitCreationAndDestructionUsingFake(){
        HabitId habitId = HabitId.newId();
        HabitId iDontExist = HabitId.newId();
        HabitId habitId2 = HabitId.newId();
        HabitTrackingUser role = new HabitTrackingUser(userId, List.of(habitId, iDontExist, habitId2));
        when(authService.habitTrackingUserFrom(userId)).thenReturn(Optional.of(new HabitTrackingUser(userId, List.of(habitId))));

        habitService.addHabit(habitId.id(), "Test Habit 1", "I must get better at TDD", userId.id());
        Habit fromRepo = habitDomainService.fromId(habitId, role).get();
        assertEquals("Test Habit 1", fromRepo.name());
        assertEquals("I must get better at TDD", fromRepo.description());

        assertEquals(Optional.empty(), habitDomainService.fromId(iDontExist, role));

        habitService.addHabit(habitId2.id(), "Test Habit 2", "I must get even better at TDD", userId.id());
        Habit anotherFromRepo = habitDomainService.fromId(habitId2, role).get();
        assertEquals("Test Habit 2", anotherFromRepo.name());
        assertEquals("I must get even better at TDD", anotherFromRepo.description());

        // Try to add a habit with ..
    }
}
