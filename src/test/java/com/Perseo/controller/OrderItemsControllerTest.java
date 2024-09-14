package com.Perseo.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderItemsControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderItemsController orderItemsController;

    @Mock
    private OrderItemsService orderItemsService;

    @Mock
    private UserService userService;

    private OrderItems order;
    private Course course;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderItemsController).build();

        // Initialize objects to be used in tests
        order = new OrderItems();
        order.setId(1L);
        order.setTotalPrice(100.0);
        order.setStatus("Pending");

        course = new Course();
        course.setId(1L);
        course.setPrice(100.0);

        user = new User();
        user.setId(1L);
    }

    @Test
    public void testCreateOrder() throws Exception {
        when(orderItemsService.saveOrder(any(OrderItems.class))).thenReturn(order);

        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"totalPrice\":100.0,\"status\":\"Pending\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetOrder() throws Exception {
        when(orderItemsService.findById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testAddCourseToOrder() throws Exception {
        when(userService.findById(1L)).thenReturn(user);
        when(orderItemsService.saveOrder(any(OrderItems.class))).thenReturn(order);

        mockMvc.perform(post("/orders/1/add-course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"price\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testAddCourseToOrder_UserNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(post("/orders/1/add-course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"price\":100.0}"))
                .andExpect(status().isNotFound());
    }
}
