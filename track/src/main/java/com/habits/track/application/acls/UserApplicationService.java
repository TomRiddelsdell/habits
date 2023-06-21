package com.habits.track.application.acls;

import com.habits.track.domain.UserId;
import com.habits.track.domain.services.UserInfo;
import com.habits.track.domain.services.UserService;

public final class UserApplicationService implements UserService {

    @Override
    public UserInfo userInfoFromId(UserId userId) {
        throw new UnsupportedOperationException("Unimplemented method 'userInfoFromId'");
    }
}
