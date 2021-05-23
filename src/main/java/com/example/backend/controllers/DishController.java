package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.backend.Services.DishService;
import com.example.backend.models.Dish;

@RestController
@CrossOrigin(origins = "http://3.131.152.182", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class DishController {


  @Autowired
  DishService dishService;


  @PostMapping("/api/restaurant/{restaurantId}/dish")
  public Dish createDishForRestaurant(@PathVariable("restaurantId") int restaurantId,
                                      @RequestBody Dish dish) {
    return dishService.createDishForRestaurant(restaurantId, dish);
  }

  @GetMapping("/api/restaurant/{restaurantId}/dishes")
  public List<Dish> sortAllDishByNameForRestaurant(@PathVariable("restaurantId") int restaurantId
                                                   ) {
    return dishService.sortAllDishByNameForRestaurant(restaurantId);
  }

  @GetMapping("/api/restaurant/{restaurantId}/dishes/{dishName}")
  public Dish findDishByName(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishName") String dishName) {
    return dishService.findDishByName(dishName);
  }

//  @GetMapping("/api/restaurant/{restaurantId}/dish/{dishId}")
//  public Dish findDishByDishId(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
//    return dishService.findDishByDishId(dishId);
//  }

  @GetMapping("/api/restaurant/{restaurantId}/dish/{dishId}")
  public Dish findDishByDishId(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
    return dishService.findDishByDishId(dishId);
  }

  @DeleteMapping("/api/dish/{dishId}")
  public void deleteDishForRestaurant(@PathVariable("dishId") int dishId) {
    dishService.deleteDishForRestaurant(dishId);
  }

  @PutMapping("/api/dish/{dishId}")
  public Dish updateDishForRestaurant(@PathVariable("dishId") int dishId,
                                      @RequestBody Dish newDish) {
    return dishService.updateDishForRestaurant(dishId, newDish);
  }
}
