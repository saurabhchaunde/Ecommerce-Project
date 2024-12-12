package com.Ecommerce.Ecommerce1.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private  int id;

   //@NotEmpty(message = " Name cannot be Empty")
    private String name;

    //@NotEmpty(message = "Brand cannot be Empty")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category Category;

    @Column(length = 1500)
    private String Description;


    private int price;

    private int discount;


}
