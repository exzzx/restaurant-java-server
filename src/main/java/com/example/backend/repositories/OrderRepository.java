package com.example.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.models.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}
