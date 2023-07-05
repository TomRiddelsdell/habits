package com.habits.track.projections;

import java.util.Set;
import java.util.UUID;

public interface HabitsByUserRepository {

    public record HabitForUser(String name, String description, UUID userID){};

    /**
     * Add a record in the repository for a newly created habit
     * @param habitId the habit id
     * @param name the name of the habit
     * @param description a description of the habit
     * @param userId the user id
     */
    void addHabitForUser(UUID habitId, String name, String description, UUID userId);

    /**
     * Retrieve all habits for a specific user
     * @param userId
     * @return
     */
    Set<HabitForUser> getHabitsForUser(UUID userId);
}
