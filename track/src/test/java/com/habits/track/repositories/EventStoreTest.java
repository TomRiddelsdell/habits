package com.habits.track.repositories;

import org.junit.jupiter.api.Test;

import com.habits.track.domain.Event;
import com.habits.track.domain.EventStore;
import com.habits.track.domain.EventStreamId;
import com.habits.track.domain.EventStreamVersion;
import com.habits.track.utils.EventDummy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class EventStoreTest {

    abstract EventStore implementationToTest();
    
    @Test
    void storeAndRetrieve(){
        EventStreamId id = new EventStreamId(UUID.randomUUID());
        var events = List.of(Event.newEvent(new EventDummy("Dummy"), EventDummy.class).build());
        implementationToTest().create(events.stream(), id); // This should increment the stored version 
        var reloaded = implementationToTest().load(id).get();
        var eventsReloaded = reloaded.events().collect(Collectors.toList());
        assertEquals(events, eventsReloaded);

        // Repeat the process with an independent stream
        EventStreamId id2 = new EventStreamId(UUID.randomUUID());
        var events2 = List.of(Event.newEvent(new EventDummy("Another Dummy"), EventDummy.class).build());
        implementationToTest().create(events2.stream(), id2); // This should increment the stored version 
        var eventsReloaded2 = implementationToTest().load(id2);
        assertEquals(events2, eventsReloaded2.get().events().collect(Collectors.toList()));

        // Append to a streams events and check the versions are incremented as expected
        var eventsToAppend = List.of(Event.newEvent(new EventDummy("Dummy"), EventDummy.class).build());
        implementationToTest().append(eventsToAppend.stream(), id, reloaded.version()); // This should increment the stored version 
        var eventsReloadedAgain = implementationToTest().load(id);
        var expected = Stream.concat(events.stream(), eventsToAppend.stream());
        assertEquals(expected.collect(Collectors.toList()), eventsReloadedAgain.get().events().collect(Collectors.toList()));

        // Repeat the append on a different stream to catch any cross effects
        var eventsToAppend2 = List.of(Event.newEvent(new EventDummy("Another Dummy To Append"), EventDummy.class).build());
        implementationToTest().append(eventsToAppend2.stream(), id2, eventsReloaded2.get().version()); // This should increment the stored version 
        var eventsReloadedAgain2 = implementationToTest().load(id2);
        var expected2 = Stream.concat(events2.stream(), eventsToAppend2.stream());
        assertEquals(expected2.collect(Collectors.toList()), eventsReloadedAgain2.get().events().collect(Collectors.toList()));
    }

    @Test
    void appendBeforeCreateThrows(){
        EventStreamId id = new EventStreamId(UUID.randomUUID());
        var events = Stream.of(Event.newEvent(new EventDummy("Dummy"), EventDummy.class).build());
        assertThrows(IllegalArgumentException.class, () -> implementationToTest().append(events, id, EventStreamVersion.initialVersion())); // This should be trying to save with an old version
    }

    @Test
    void recreationThrows(){
        EventStreamId id = new EventStreamId(UUID.randomUUID());
        var events = Stream.of(Event.newEvent(new EventDummy("Dummy"), EventDummy.class).build());
        implementationToTest().create(events, id); // This should increment the stored version 
        assertThrows(IllegalArgumentException.class, () -> implementationToTest().create(events, id)); // This should be trying to save with an old version
    }

    @Test
    void versionMismatchFails(){
        EventStreamId id = new EventStreamId(UUID.randomUUID());
        var events = List.of(Event.newEvent(new EventDummy("Dummy"), EventDummy.class).build());
        implementationToTest().create(events.stream(), id); // At this point the stored version is 0
        var eventsToAppend = Stream.of(Event.newEvent(new EventDummy("Dummy to append"), EventDummy.class).build());
        implementationToTest().append(eventsToAppend, id, EventStreamVersion.initialVersion()); // This should increment the stored version from 0 to 1
        implementationToTest().append(Stream.empty(), id, new EventStreamVersion(1)); // Appending empty list increments version from 1 to 2 
        assertThrows(IllegalArgumentException.class, () -> implementationToTest().append(Stream.empty(), id, new EventStreamVersion((1)))); // Appending empty list always succeeds
        assertThrows(IllegalArgumentException.class, () -> implementationToTest().append(events.stream(), id, EventStreamVersion.initialVersion())); // This should be trying to save with an old version
        assertThrows(IllegalArgumentException.class, () -> implementationToTest().append(events.stream(), id, new EventStreamVersion(3))); // This should be trying to save with a future
    }

    @Test
    void versionIsIncremented(){
        EventStreamId id = new EventStreamId(UUID.randomUUID());
        var events = Stream.of(Event.newEvent(new EventDummy("Dummy"), EventDummy.class).build());
        implementationToTest().create(events, id); 
        var storedVersion = implementationToTest().load(id).get().version();
        var eventsToAppend = Stream.of(Event.newEvent(new EventDummy("Dummy to append"), EventDummy.class).build());
        implementationToTest().append(eventsToAppend, id, EventStreamVersion.initialVersion()); // This should increment the stored version 
        var storedVersion2 = implementationToTest().load(id).get().version();
        assertTrue(storedVersion.version() < storedVersion2.version());
    }
}