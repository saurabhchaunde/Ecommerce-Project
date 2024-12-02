package com.Ecommerce.Ecommerce1.Controller;

import com.Ecommerce.Ecommerce1.Entity.Order;
import com.Ecommerce.Ecommerce1.Exception.OrderNotFoundException;
import com.Ecommerce.Ecommerce1.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
        order = Order.builder()
                .id(1)
                .productId(1)
                .quantity(2)
                .Amount(2000)
                .build();
    }

    @Test
    void createOrder() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.productId").value(order.getProductId()))
                .andExpect(jsonPath("$.quantity").value(order.getQuantity()))
                .andExpect(jsonPath("$.amount").value(order.getAmount()));

        verify(orderService, times(1)).createOrder(any(Order.class));
    }

    @Test
    void updateOrder() throws Exception {
        when(orderService.updateOrder(anyInt(), any(Order.class))).thenReturn(order);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.productId").value(order.getProductId()))
                .andExpect(jsonPath("$.quantity").value(order.getQuantity()))
                .andExpect(jsonPath("$.amount").value(order.getAmount()));

        verify(orderService, times(1)).updateOrder(anyInt(), any(Order.class));
    }

    @Test
    void updateOrder_NotFound() throws Exception {
        when(orderService.updateOrder(anyInt(), any(Order.class))).thenThrow(new OrderNotFoundException("Order not found with ID: 1"));

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).updateOrder(anyInt(), any(Order.class));
    }

    @Test
    void deleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(anyInt());

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(anyInt());
    }

    @Test
    void deleteOrder_NotFound() throws Exception {
        doThrow(new OrderNotFoundException("Order not found with ID: 1")).when(orderService).deleteOrder(anyInt());

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).deleteOrder(anyInt());
    }

    @Test
    void getOrderById() throws Exception {
        when(orderService.getOrderById(anyInt())).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.productId").value(order.getProductId()))
                .andExpect(jsonPath("$.quantity").value(order.getQuantity()))
                .andExpect(jsonPath("$.amount").value(order.getAmount()));

        verify(orderService, times(1)).getOrderById(anyInt());
    }

    @Test
    void getOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(anyInt())).thenThrow(new OrderNotFoundException("Order not found with ID: 1"));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).getOrderById(anyInt());
    }

    @Test
    void getAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(order));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order.getId()))
                .andExpect(jsonPath("$[0].productId").value(order.getProductId()))
                .andExpect(jsonPath("$[0].quantity").value(order.getQuantity()))
                .andExpect(jsonPath("$[0].amount").value(order.getAmount()));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getAllOrders_EmptyList() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(orderService, times(1)).getAllOrders();
    }
}
