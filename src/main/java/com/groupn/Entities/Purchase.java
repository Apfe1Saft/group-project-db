package com.groupn.Entities;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private int id;
    private LocalDate dateOfPurchase;
    private int price;
    private ArtObject artObject;
    private Owner seller;
    private Owner buyer;
}
