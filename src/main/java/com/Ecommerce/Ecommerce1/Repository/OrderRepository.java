package com.Ecommerce.Ecommerce1.Repository;

import com.Ecommerce.Ecommerce1.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
