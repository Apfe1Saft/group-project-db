package com.groupn.Entities;

public enum LocationType {
    MUSEUM(1),
    PRIVATE_LOCATION(2);
    private int id;

    LocationType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
