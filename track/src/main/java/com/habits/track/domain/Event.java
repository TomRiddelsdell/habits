package com.habits.track.domain;

import java.util.UUID;

// TODO: Extract out into separate interface - when we introduce a non-native event store
public class Event<T> {

  private UUID eventId;
  private String eventType;
  private T data;
  private String encryptedData;

  public UUID eventId() {
    return eventId;
  }

  public String eventType() {
    return eventType;
  }

  public T data() {
    return data;
  }

  public String encryptedData() {
    return encryptedData;
  }

  public static <T> Event.TypedBuilder<T> newEvent(Class<T> eventType) {
    return new Event.TypedBuilder<>(eventType);
  }

  public static <T> Event.TypedBuilder<T> newEvent(T data, Class<T> eventType) {
    return new TypedBuilder<>(eventType).data(data);
  }

  public static class TypedBuilder<T> {

    private UUID eventId;
    private final String eventType;
    private T data;
    private String encryptedData;

    public TypedBuilder(String eventType) {
      this.eventId = UUID.randomUUID();
      this.eventType = eventType;
    }

    public TypedBuilder(Class<T> eventType) {
      this(eventType.getSimpleName());
    }

    public TypedBuilder<T> eventId(UUID eventId) {
      this.eventId = eventId;
      return this;
    }

    public TypedBuilder<T> data(T dataObject) {
      this.data = dataObject;
      return this;
    }

    public TypedBuilder<T> eventId(String eventId) {
      return eventId(UUID.fromString(eventId));
    }

    public TypedBuilder<T> encryptedData(String data) {
      this.encryptedData = data;
      return this;
    }

    public Event<T> build() {
      Event<T> event = new Event<>();
      event.eventId = eventId;
      event.eventType = eventType;
      event.data = data;
      event.encryptedData = encryptedData;
      return event;
    }

  }
}