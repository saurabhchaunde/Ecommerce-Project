package com.Ecommerce.Ecommerce1.Entity;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Category {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @Column(length = 255, nullable = false, unique = true)
    private String name;

}
