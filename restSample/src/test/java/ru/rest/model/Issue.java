package ru.rest.model;

import java.util.Objects;

public class Issue {

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
        Issue issue = (Issue) o;
        return id == issue.id &&
                Objects.equals(description, issue.description) &&
                Objects.equals(subject, issue.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, subject);
    }

    public int getId() {
        return id;
    }

    public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issue withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Issue withSubject(String subject) {
        this.subject = subject;
        return this;
    }
}
