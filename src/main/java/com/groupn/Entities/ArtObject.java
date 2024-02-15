package com.groupn.Entities;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtObject {
    private int id;
    private String name;
    private String description;
    private Author author;
    private Owner owner;
    private Location location;
    private LocalDate dateOfCreation;
    private List<Event> events;
    private List<Purchase> purchases;
}
