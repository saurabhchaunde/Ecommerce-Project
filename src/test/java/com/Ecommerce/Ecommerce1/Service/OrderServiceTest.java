package com.Ecommerce.Ecommerce1.Service;

import com.Ecommerce.Ecommerce1.Entity.Order;
import com.Ecommerce.Ecommerce1.Entity.Product;
import com.Ecommerce.Ecommerce1.Exception.OrderNotFoundException;
import com.Ecommerce.Ecommerce1.Exception.ProductNotFoundException;
import com.Ecommerce.Ecommerce1.Repository.OrderRepository;
import com.Ecommerce.Ecommerce1.Repository.ProductRepository;
import com.Ecommerce.Ecommerce1.Service.implementation.OrderServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceimpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder() {
        Product product = new Product();
        product.setId(1);
        product.setPrice(100);
        Order order = new Order();
        order.setQuantity(2);
        order.setProductId(1);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(200, createdOrder.getAmount());
        verify(productRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void createOrder_ProductNotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        Order order = new Order();
        order.setProductId(1);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            orderService.createOrder(order);
        });

        assertEquals("Category not found with ID: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void updateOrder() {
        Order existingOrder = new Order();
        existingOrder.setId(1);
        Order updatedOrder = new Order();
        updatedOrder.setId(1);
        updatedOrder.setQuantity(3);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(1, updatedOrder);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(orderRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).save(updatedOrder);
    }

    @Test
    void updateOrder_OrderNotFound() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        Order order = new Order();
        order.setId(1);

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.updateOrder(1, order);
        });

        assertEquals("Order not found with ID: 1", exception.getMessage());
        verify(orderRepository, times(1)).findById(1);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void deleteOrder() {
        when(orderRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(orderRepository).deleteById(anyInt());

        assertDoesNotThrow(() -> {
            orderService.deleteOrder(1);
        });

        verify(orderRepository, times(1)).existsById(1);
        verify(orderRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteOrder_OrderNotFound() {
        when(orderRepository.existsById(anyInt())).thenReturn(false);

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.deleteOrder(1);
        });

        assertEquals("Order not found with ID: 1", exception.getMessage());
        verify(orderRepository, times(1)).existsById(1);
        verify(orderRepository, never()).deleteById(anyInt());
    }

    @Test
    void getOrderById() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1);

        assertNotNull(foundOrder);
        assertEquals(1, foundOrder.getId());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void getOrderById_NotFound() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrderById(1);
        });

        assertEquals("Order not found with ID: 1", exception.getMessage());
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void getAllOrders() {
        Order order1 = new Order();
        order1.setId(1);
        Order order2 = new Order();
        order2.setId(2);
        List<Order> orderList = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }
}
