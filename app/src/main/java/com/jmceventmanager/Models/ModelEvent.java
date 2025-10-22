package com.jmceventmanager.Models;
public class ModelEvent {
    private int eventId;
    private String eventName;
    private String eventCategory;
    private String eventDateTime;
    private String venue;
    private int capacity;
    private int currentRegistrations;
    private String eventStatus;
    private String organizerName;

    // Constructors
    public ModelEvent() {}

    public ModelEvent(String eventName, String eventCategory, String eventDateTime,
                      String venue, int capacity, String organizerName) {
        this.eventName = eventName;
        this.eventCategory = eventCategory;
        this.eventDateTime = eventDateTime;
        this.venue = venue;
        this.capacity = capacity;
        this.organizerName = organizerName;
        this.currentRegistrations = 0;
        this.eventStatus = "Active";
    }

    // Getters and setters
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getEventCategory() { return eventCategory; }
    public void setEventCategory(String eventCategory) { this.eventCategory = eventCategory; }

    public String getEventDateTime() { return eventDateTime; }
    public void setEventDateTime(String eventDateTime) { this.eventDateTime = eventDateTime; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getCurrentRegistrations() { return currentRegistrations; }
    public void setCurrentRegistrations(int currentRegistrations) { this.currentRegistrations = currentRegistrations; }

    public String getEventStatus() { return eventStatus; }
    public void setEventStatus(String eventStatus) { this.eventStatus = eventStatus; }

    public String getOrganizerName() { return organizerName; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }

    public double getUtilizationRate() {
        return capacity > 0 ? (double) currentRegistrations / capacity * 100 : 0;
    }
}