package com.Ecommerce.Ecommerce1.Service.implementation;

import com.Ecommerce.Ecommerce1.Entity.Order;
import com.Ecommerce.Ecommerce1.Entity.Product;
import com.Ecommerce.Ecommerce1.Exception.OrderNotFoundException;
import com.Ecommerce.Ecommerce1.Exception.ProductNotFoundException;
import com.Ecommerce.Ecommerce1.Repository.OrderRepository;
import com.Ecommerce.Ecommerce1.Repository.ProductRepository;
import com.Ecommerce.Ecommerce1.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceimpl implements OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order createOrder(Order order) {
        int quantity=order.getQuantity();
        int productid= order.getProductId();
        Optional<Product> p=productRepository.findById(productid);
        Product product = p.orElseThrow(() ->
                new ProductNotFoundException("Category not found with ID: " + productid));
        int price=product.getPrice();
        int discount=product.getDiscount();
        int Amount=(quantity*price);
        int disAmout=(Amount*discount)/100;
        order.setAmount(Amount-disAmout);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(int id, Order order) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        int productId = order.getProductId();
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.orElseThrow(() ->
                new ProductNotFoundException("Product not found with ID: " + productId));
        int price = product.getPrice();
        int discount = product.getDiscount();
        int quantity = order.getQuantity();
        int amount = quantity * price;
        int disAmount = (amount * discount) / 100;

        order.setAmount(amount - disAmount);

        order.setId(id);

        return orderRepository.save(order);
    }


    @Override
    public void deleteOrder(int id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}