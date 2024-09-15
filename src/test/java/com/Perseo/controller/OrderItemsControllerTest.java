package com.Perseo.controller;

import com.Perseo.exception.ResourceNotFoundException;
import com.Perseo.model.Course;
import com.Perseo.model.OrderItems;
import com.Perseo.model.User;
import com.Perseo.service.OrderItemsService;
import com.Perseo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class OrderItemsControllerTest {

    @Mock
    private OrderItemsService orderService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderItemsController orderController;

    private OrderItems orderItems;
    private User user;
    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        course = new Course();
        course.setId(1L);
        course.setPrice(100.0);

        orderItems = new OrderItems();
        orderItems.setId(1L);
        orderItems.setUser(user);
        orderItems.setCourses(List.of(course));
        orderItems.setTotalPrice(100.0);
        orderItems.setStatus("Pending");
    }

    @Test
    public void test_Create_OrderItems() {
        when(orderService.saveOrder(any(OrderItems.class))).thenReturn(orderItems);

        ResponseEntity<OrderItems> response = orderController.createOrder(orderItems);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderItems, response.getBody());
    }

    @Test
    public void test_Get_OrderItems() {
        when(orderService.findById(anyLong())).thenReturn(orderItems);

        ResponseEntity<OrderItems> response = orderController.getOrder(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderItems, response.getBody());
    }

    @Test
    public void test_Update_OrderItems() {
        when(orderService.updateOrder(anyLong(), any(OrderItems.class))).thenReturn(orderItems);

        ResponseEntity<OrderItems> response = orderController.updateOrder(1L, orderItems);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderItems, response.getBody());
    }

    @Test
    public void test_Delete_OrderItems() {
        doNothing().when(orderService).deleteOrder(anyLong());

        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_Add_Course_To_OrderItems() {
        when(userService.findById(anyLong())).thenReturn(user);
        when(orderService.saveOrder(any(OrderItems.class))).thenReturn(orderItems);

        ResponseEntity<OrderItems> response = orderController.addCourseToOrder(1L, course);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderItems, response.getBody());
    }
}
