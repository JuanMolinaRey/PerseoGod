package com.Perseo.service;

import com.Perseo.model.OrderItems;
import com.Perseo.repository.IOrderRepository;
import com.Perseo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderItemsServiceTest {

    @InjectMocks
    private OrderItemsService orderItemsService;

    @Mock
    private IOrderRepository orderRepository;

    private OrderItems order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        order = new OrderItems();
        order.setId(1L);
        order.setTotalPrice(100.0);
        order.setStatus("Pending");
    }

    @Test
    public void testSaveOrder() {
        when(orderRepository.save(any(OrderItems.class))).thenReturn(order);

        OrderItems savedOrder = orderItemsService.saveOrder(order);

        assertNotNull(savedOrder);
        assertEquals(1L, savedOrder.getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testFindById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderItems foundOrder = orderItemsService.findById(1L);

        assertNotNull(foundOrder);
        assertEquals(1L, foundOrder.getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderItemsService.findById(1L);
        });
    }

    @Test
    public void testUpdateOrder() {
        OrderItems updatedOrder = new OrderItems();
        updatedOrder.setId(1L);
        updatedOrder.setTotalPrice(200.0);
        updatedOrder.setStatus("Completed");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(OrderItems.class))).thenReturn(updatedOrder);

        OrderItems result = orderItemsService.updateOrder(1L, updatedOrder);

        assertNotNull(result);
        assertEquals(200.0, result.getTotalPrice());
        assertEquals("Completed", result.getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(updatedOrder);
    }

    @Test
    public void testUpdateOrder_OrderNotFound() {
        OrderItems updatedOrder = new OrderItems();
        updatedOrder.setId(1L);
        updatedOrder.setTotalPrice(200.0);
        updatedOrder.setStatus("Completed");

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderItemsService.updateOrder(1L, updatedOrder);
        });
    }

    @Test
    public void testDeleteOrder() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(1L);

        assertDoesNotThrow(() -> orderItemsService.deleteOrder(1L));
        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteOrder_OrderNotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            orderItemsService.deleteOrder(1L);
        });
    }
}
