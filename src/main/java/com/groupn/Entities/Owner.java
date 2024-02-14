package com.groupn.Entities;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private int id;
    private String name;
    private String description;
    private List<ArtObject> artObjects;
    private List<Purchase> purchases;
}
