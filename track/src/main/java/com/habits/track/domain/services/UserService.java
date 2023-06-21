package com.habits.track.domain.services;

import com.habits.track.domain.UserId;

public interface UserService {
    
    public UserInfo userInfoFromId(UserId userId);
}
