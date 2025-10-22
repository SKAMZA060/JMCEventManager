package com.jmceventmanager.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registration {
    private int registrationId;
    private int eventId;
    private Participant participant;
    private String registrationTimestamp;

    public Registration(int eventId, Participant participant) {
        this.eventId = eventId;
        this.participant = participant;
        this.registrationTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());
    }

    // Getters and setters
    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public Participant getParticipant() { return participant; }
    public void setParticipant(Participant participant) { this.participant = participant; }

    public String getRegistrationTimestamp() { return registrationTimestamp; }
    public void setRegistrationTimestamp(String registrationTimestamp) { this.registrationTimestamp = registrationTimestamp; }
}