package com.Perseo.service;

import com.Perseo.model.OrderItems;

import com.Perseo.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private IOrderRepository iorderRepository;

    public OrderItems saveOrder(OrderItems order) {
        return iorderRepository.save(order);
    }

    public OrderItems findById(Long id) {
        return iorderRepository.findById(id).orElse(null);
    }
}
