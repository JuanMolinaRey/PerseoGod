package com.Perseo.service;

import com.Perseo.model.OrderItems;
import com.Perseo.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        OrderItems foundOrder = orderItemsService.findById(1L);

        assertNotNull(foundOrder);
        assertEquals(1L, foundOrder.getId());
        verify(orderRepository, times(1)).findById(1L);
    }
}
