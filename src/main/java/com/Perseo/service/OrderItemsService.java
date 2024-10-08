package com.Perseo.service;

import com.Perseo.model.OrderItems;
import com.Perseo.repository.IOrderRepository;
import com.Perseo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemsService {
    @Autowired
    private IOrderRepository iOrderRepository;

    public OrderItems saveOrder(OrderItems order) {
        return iOrderRepository.save(order);
    }

    public OrderItems findById(Long id) {
        return iOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
    }

    public OrderItems updateOrder(Long id, OrderItems updatedOrder) {
        OrderItems existingOrder = iOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        existingOrder.setUser(updatedOrder.getUser());
        existingOrder.setCourses(updatedOrder.getCourses());
        existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
        existingOrder.setStatus(updatedOrder.getStatus());

        return iOrderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        if (!iOrderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with ID: " + id);
        }

        iOrderRepository.deleteById(id);
    }
}
