package com.Ecommerce.Ecommerce1.Service.implementation;
import com.Ecommerce.Ecommerce1.Entity.Category;
import com.Ecommerce.Ecommerce1.Entity.Order;
import com.Ecommerce.Ecommerce1.Entity.Product;
import com.Ecommerce.Ecommerce1.Exception.CategoryNotFoundException;
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
        //int disamout=discount/100;
        int Amount=(quantity*price);
        int disAmout=(Amount*discount)/100;
        order.setAmount(Amount-disAmout);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(int id, Order order) {
        // Fetch the existing order from the database
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        // Fetch the product details (like price and discount) to calculate the amount and discount
        int productId = order.getProductId();
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.orElseThrow(() ->
                new ProductNotFoundException("Product not found with ID: " + productId));

        // Get the price and discount of the product
        int price = product.getPrice();
        int discount = product.getDiscount();

        // Calculate the total amount (quantity * price)
        int quantity = order.getQuantity();
        int amount = quantity * price;

        // Calculate the discount amount and subtract it from the total amount
        int disAmount = (amount * discount) / 100;

        // Set the total amount with the discount applied
        order.setAmount(amount - disAmount);

        // Set the ID of the order (to update the existing order)
        order.setId(id);

        // Save the updated order to the repository
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