package com.adan.productservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "products")
public class Product {
    @Id
    private String Id;
    private String name;
    private String description;
    private BigDecimal price;
}
