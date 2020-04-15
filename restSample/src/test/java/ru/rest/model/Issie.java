package ru.rest.model;

import java.util.Objects;

public class Issie {

    private int id;
    private String description;
    private String subject;
    private String state_name;

    public String getState() {
        return state_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issie issie = (Issie) o;
        return id == issie.id &&
                Objects.equals(description, issie.description) &&
                Objects.equals(subject, issie.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, subject);
    }

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
