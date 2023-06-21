
package com.habits.track.domain;

public class EventDispatchGenericTest{

    /*@Test
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

    interface StringHandler extends Handler<String>{}
    interface IntegerHandler extends Handler<Integer>{}

    static class EventHandlerClass implements StringHandler, IntegerHandler{

        public void handle(String element) {
            System.out.println("Handling String: " + element);
            // Your code to handle String elements
        }

        public void handle(Integer element) {
            System.out.println("Handling Integer: " + element);
            // Your code to handle Integer elements
        }
    }

    interface Handler<D>{
        void handle(D data);
    }

    static abstract class Events<A extends Handler<D>,D> {

        abstract D getValue();

        void dispatchTo(A aggregate){
            aggregate.handle(this.getValue());
        }
    }

    static class EventWithString extends Events<EventHandlerClass,String> {
        private String value;

        public EventWithString(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    static class EventWithInteger extends Events<EventHandlerClass,Integer> {
        private Integer value;

        public EventWithInteger(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }*/
}
