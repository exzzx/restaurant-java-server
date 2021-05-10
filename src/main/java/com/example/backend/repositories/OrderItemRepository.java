package com.example.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.models.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

}
