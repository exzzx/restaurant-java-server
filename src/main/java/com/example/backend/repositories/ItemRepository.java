package com.example.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.backend.models.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {

}
