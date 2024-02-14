package com.groupn.Entities;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private int id;
    private EventType type;
    private String description;
    private LocalDate startDateOfEvent;
    private LocalDate endDateOfEvent;
    private int price;
}
