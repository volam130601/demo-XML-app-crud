package com.example.demoxmlapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private String id;
    private String name;
    private String price;
    private String description;
    private Integer calories;
}
