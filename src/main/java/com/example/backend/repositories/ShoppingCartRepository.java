package com.example.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.models.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {

}
