package com.habits.track.domain;

import java.util.stream.Stream;

import com.habits.track.application.EventStream;

public record Habit(HabitId id, String name, String description, HabitTrackingUser user) {
    
    public Habit(HabitState builder){
        this(builder.id, builder.name, builder.description, builder.user);
    }

    public Stream<Event<?>> createHabit(HabitId id, String name, String description, HabitTrackingUser user){
        return Stream.of(Event.newEvent(new HabitCreated(id.id(), name, description, user.id().id()), HabitCreated.class).build());
    }
    public static class HabitState {
        private HabitId id;
        private String name;
        private String description;
        private HabitTrackingUser user;

        // we need a default constructer to create the initial state 
        // that will handle the events
        public HabitState(){
            this.id = null;
            this.name = "";
            this.description = "";
            this.user = null;
        }

        /**
         * Applies all events in the event stream in order to reconstruct the HabitState. 
         * This could be tidied up when the pattern matching switch is available in java 17 preview
         * @param events
         * @return
         */
        public HabitState applyEvents(EventStream events) throws IllegalArgumentException{
            events.events().forEachOrdered( (event) -> {
                if(event.data() instanceof HabitCreated) apply((HabitCreated)event.data());
                else if(event.data() instanceof HabitModified) apply((HabitModified)event.data());
                else throw new IllegalArgumentException("event type not supported by HabitState");
            });

            return this;
        }


        private HabitState apply(HabitCreated event)
        {
            this.name = event.name();
            this.description = event.description();
            return this;
        }

        private HabitState apply(HabitModified event)
        {
            this.name = event.name();
            this.description = event.description();
            return this;
        }

        public Habit build(){
            return new Habit(this);
        }

        public HabitState id(HabitId id){
            this.id = id;
            return this;
        }

        public HabitState name(String name){
            this.name = name;
            return this;
        }

        public HabitState description(String description){
            this.description = description;
            return this;
        }

        public HabitState user(HabitTrackingUser user){
            this.user = user;
            return this;
        }
    }
}
