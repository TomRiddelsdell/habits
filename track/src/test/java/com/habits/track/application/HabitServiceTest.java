package com.habits.track.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Set;

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
import com.habits.track.projections.HabitsByUserRepository;
import com.habits.track.projections.HabitsByUserRepository.HabitForUser;
import com.habits.track.repositories.EventStoreInMemory;
import com.habits.track.repositories.HabitsByUserRepositoryInMemory;

@ExtendWith(MockitoExtension.class)
public class HabitServiceTest {
 
    HabitService habitService;
    HabitDomainService habitDomainService;
    AuthorizationDomainService authService;
    HabitsByUserRepository habitsByUserRepo;
    UserId userId;

    @BeforeEach
    void init(@Mock AuthorizationDomainService userService){
        userId = new UserId(UUID.randomUUID());
        this.authService = userService;
        var eventStore = new EventStoreInMemory();
        habitDomainService = new HabitDomainService(eventStore);
        habitsByUserRepo = new HabitsByUserRepositoryInMemory();
        habitService = new HabitService(eventStore, userService, habitsByUserRepo);
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

        // Test the projection
        Set<HabitForUser> view = habitsByUserRepo.getHabitsForUser(role.id().id());
        assertEquals(true, view.contains(new HabitForUser("Test Habit 1", "I must get better at TDD", userId.id())));
        assertEquals(true, view.contains(new HabitForUser("Test Habit 2", "I must get even better at TDD", userId.id())));
        assertEquals(2, view.size());
    }
}
