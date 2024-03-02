package com.groupn.entities;

import lombok.*;

import java.time.LocalDate;

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
    private LocalDate startDateOfEvent;
    private LocalDate EndDateOfEvent;
    private Location location;
    private int price;
}
