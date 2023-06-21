package com.habits.track.domain.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.habits.track.domain.HabitId;
import com.habits.track.domain.HabitTrackingUser;
import com.habits.track.domain.UserId;
import com.habits.track.domain.services.AuthorizationDomainService;

public final class AuthorizationDomainService {
    
    private UserService userService;

    public AuthorizationDomainService(UserService userService){
        this.userService = userService;
    }

    public Optional<HabitTrackingUser> habitTrackingUserFrom(UserId id){
        UserInfo info = userService.userInfoFromId(id);

        if(!info.roles().contains("HabitTrackingUser"))
            return Optional.empty();

        return Optional.of(new HabitTrackingUser(id, info.habits().stream().map((UUID x) -> new HabitId(x)).collect(Collectors.toList())));
    }
}
