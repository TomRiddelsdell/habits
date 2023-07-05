package com.habits.track.domain;

import java.util.Set;

public interface EventListener extends java.util.EventListener{

    public void onEvent(Event<?> event);

    public Set<Class<?>> listensTo();
}
