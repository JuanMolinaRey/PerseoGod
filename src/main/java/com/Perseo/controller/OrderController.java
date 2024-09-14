package com.Perseo.controller;

import com.Perseo.exception.ResourceNotFoundException;
import com.Perseo.model.Course;
import com.Perseo.model.OrderItems;
import com.Perseo.model.User;
import com.Perseo.service.OrderService;
import com.Perseo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public OrderItems createOrder(@RequestBody OrderItems order) {
        return orderService.saveOrder(order);
    }

    @GetMapping("/{id}")
    public OrderItems getOrder(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping("/{userId}/add-course")
    public ResponseEntity<OrderItems> addCourseToOrder(@PathVariable Long userId, @RequestBody Course course) {
        User user = userService.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        OrderItems order = new OrderItems();
        order.setUser(user);
        order.setCourses(List.of(course));
        order.setTotalPrice(course.getPrice());
        order.setStatus("Pending");

        OrderItems savedOrder = orderService.saveOrder(order);

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}
