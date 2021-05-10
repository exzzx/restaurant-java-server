package com.example.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.models.ShoppingCartItem;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem, Integer> {


}
