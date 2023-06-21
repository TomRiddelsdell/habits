
package com.habits.track.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EventDispatchTest{

    @Test
    void testvisitorPattern(){
        List<Events> elements = new ArrayList<>();
        elements.add(new EventWithString("Hello"));
        elements.add(new EventWithInteger(42));
        // Add more elements of different types

        EventHandlerClass visitor = new EventHandlerClass();
        for (Events element : elements) {
            element.dispatchTo(visitor);
        }
    }

    static class EventHandlerClass {

        public void handle(String element) {
            System.out.println("Handling String: " + element);
            // Your code to handle String elements
        }

        public void handle(Integer element) {
            System.out.println("Handling Integer: " + element);
            // Your code to handle Integer elements
        }
    }

    interface Events {
        void dispatchTo(EventHandlerClass aggregate);
    }

    static class EventWithString implements Events {
        private String value;

        public EventWithString(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public void dispatchTo(EventHandlerClass visitor) {
            visitor.handle(value);
        }
    }

    static class EventWithInteger implements Events {
        private Integer value;

        public EventWithInteger(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public void dispatchTo(EventHandlerClass visitor) {
            visitor.handle(value);
        }
    }
}
