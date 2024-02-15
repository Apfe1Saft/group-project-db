package com.groupn.entities;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private int id;
    private String name;
    private String description;
    private LocalDate dateOfOpening;
    private String placement;
    private LocationType type;
    private List<ArtObject> artObjects;

}
