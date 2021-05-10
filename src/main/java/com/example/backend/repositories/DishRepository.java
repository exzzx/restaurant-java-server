package com.example.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.example.backend.models.Dish;


public interface DishRepository extends CrudRepository<Dish, Integer> {

  @Query("SELECT s FROM Dish s WHERE s.name=:name")
  Optional<Dish> findDishByName(@Param("name") String name);

  @Query("SELECT s FROM Dish s WHERE s.restaurant.id=:id")
  List<Dish> findDishById(@Param("id") int id);

  @Query("SELECT s FROM Dish s WHERE s.id=:id")
  Dish findDishByDishId(@Param("id") int id);

}
