package com.Perseo.controller;

import com.Perseo.exception.ResourceNotFoundException;
import com.Perseo.model.Course;
import com.Perseo.model.OrderItems;
import com.Perseo.model.User;
import com.Perseo.service.OrderItemsService;
import com.Perseo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderItemsController {

    @Autowired
    private OrderItemsService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<OrderItems> createOrder(@RequestBody OrderItems order) {
        OrderItems savedOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItems> getOrder(@PathVariable Long id) {
        try {
            OrderItems order = orderService.findById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItems> updateOrder(@PathVariable Long id, @RequestBody OrderItems updatedOrder) {
        try {
            OrderItems order = orderService.updateOrder(id, updatedOrder);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
