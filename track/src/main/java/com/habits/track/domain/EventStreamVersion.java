package com.habits.track.domain;

public record EventStreamVersion(int version) {

    public static EventStreamVersion initialVersion(){
        return new EventStreamVersion(0);
    }
}
