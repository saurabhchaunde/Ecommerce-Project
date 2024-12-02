package com.Ecommerce.Ecommerce1.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class Users {
    @Id
    private int id;
    private String username;
    private String password;
    private String role;

}
