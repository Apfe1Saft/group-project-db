package com.groupn.Entities;

public enum EventType {
    Exhebition(1),
    Wrongdoor(2);
    private int id;
    EventType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
