package com.Ecommerce.Ecommerce1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")  // Renaming the table to "orders"
public class Order {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private int userId;

    private int productId;

    private int quantity;

    private int Amount;

}
