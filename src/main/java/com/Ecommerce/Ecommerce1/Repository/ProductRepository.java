package com.Ecommerce.Ecommerce1.Repository;

import com.Ecommerce.Ecommerce1.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
