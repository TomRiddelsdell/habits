package com.habits.track.repositories;

import com.habits.track.domain.EventStore;

public class EventStoreInMemoryTest extends EventStoreTest{

    EventStore toTest = new EventStoreInMemory();

    @Override
    EventStore implementationToTest(){
        return this.toTest;
    }
}