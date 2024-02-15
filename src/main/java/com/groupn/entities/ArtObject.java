package com.groupn.entities;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtObject {
    private int id;
    private String name;
    private String description;
    private LocalDate dateOfCreation;
}
