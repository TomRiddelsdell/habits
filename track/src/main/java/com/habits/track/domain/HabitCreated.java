package com.habits.track.domain;

import java.util.UUID;

public record HabitCreated(UUID id, String name, String description) implements HabitEvent{

}
