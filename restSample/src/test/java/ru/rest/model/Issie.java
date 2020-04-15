package ru.rest.model;

public class Issie {

    private int id;
    private String description;
    private String subject;
    private String state;

    public int getId() {
        return id;
    }

    public Issie withId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issie withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Issie withSubject(String subject) {
        this.subject = subject;
        return this;
    }
}
