package com.habits.track.repositories;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.habits.track.projections.HabitsByUserRepository;

public class HabitsByUserRepositoryInMemory implements HabitsByUserRepository{


    Map<UUID, Set<HabitForUser>> store = new HashMap<>();

    @Override
    public void addHabitForUser(UUID habitId, String name, String habitDescription, UUID userId) {
        if(store.containsKey(userId))
            store.get(userId).add(new HabitForUser(name, habitDescription, userId));
        else   
            store.put(userId, new HashSet<>(){{add(new HabitForUser(name, habitDescription, userId));}});
    }

    @Override
    public Set<HabitForUser> getHabitsForUser(UUID userId) {
        if(store.containsKey(userId))
            return store.get(userId);
        else   
            return new HashSet<>();
    }
    
    public String toString(){
        return store.entrySet().stream()
            .map(entry -> 
                entry.getKey() + ": " + entry.getValue().stream().map(h -> h.name()).collect(Collectors.joining(", ")))
            .collect(Collectors.joining("\n"));
    }
}
