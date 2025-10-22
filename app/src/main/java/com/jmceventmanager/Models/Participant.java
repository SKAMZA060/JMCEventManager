package com.jmceventmanager.Models;


public class Participant {
    private int participantId;
    private String participantName;
    private String email;
    private String phone;

    // Constructors
    public Participant() {}

    public Participant(String participantName, String email, String phone) {
        this.participantName = participantName;
        this.email = email;
        this.phone = phone;
    }

    // Getters and setters
    public int getParticipantId() { return participantId; }
    public void setParticipantId(int participantId) { this.participantId = participantId; }

    public String getParticipantName() { return participantName; }
    public void setParticipantName(String participantName) { this.participantName = participantName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}