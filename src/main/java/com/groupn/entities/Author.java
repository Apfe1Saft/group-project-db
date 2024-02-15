package com.groupn.entities;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private int id;
    private String name;
    private String description;
    private LocalDate dateOfBirth;
    private List<ArtObject> artObjects;
}
