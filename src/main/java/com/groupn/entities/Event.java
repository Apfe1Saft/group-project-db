package com.groupn.entities;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private int id;
    private String name;
    private EventType type;
    private String description;
    private String startDateOfEvent;
    private String endDateOfEvent;
    private Location location;
    private int price;
    private List<ArtObject> artObjects;
}
