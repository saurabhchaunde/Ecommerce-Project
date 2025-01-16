package com.Ecommerce.Ecommerce1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

//it store the value in the database
@Entity

//This are the lombok Annotation for simpilycity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//used to simplify the construction of objects by providing a builder pattern.
@Builder

//by this annotation we can set name of the table
@Table(name = "orders")
public class Order {

    //By this we can mention id as primary key in the our table and it will genarated the Automatically and unique
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private int userId;

    private int productId;

    private int quantity;

    private int Amount;

}
