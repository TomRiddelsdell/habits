package com.habits.track.domain;

import java.util.UUID;


public record HabitCreated(UUID habitId, String name, String description, UUID habitTrackingUserId) {
}
