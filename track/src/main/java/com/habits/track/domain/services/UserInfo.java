package com.habits.track.domain.services;

import java.util.List;
import java.util.UUID;

public record UserInfo(List<String> roles, List<UUID> habits) {}
