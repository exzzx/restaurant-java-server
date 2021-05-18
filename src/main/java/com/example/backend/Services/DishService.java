package com.example.backend.Services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.example.backend.models.Dish;
import com.example.backend.models.Restaurant;
import com.example.backend.repositories.DishRepository;
import com.example.backend.repositories.RestaurantRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import static java.lang.Integer.parseInt;

@Service
//@CrossOrigin(origins = "*", allowCredentials = "true")
public class DishService {


  @Autowired
  DishRepository dishRepo;

  @Autowired
  RestaurantRepository restRepo;

  public Dish createDishForRestaurant(int restaurantId, Dish dish) {
    Optional<Restaurant> data = restRepo.findById(restaurantId);

    if (!data.isPresent()) {
      return null;
    } else {
      Restaurant rest = data.get();
      Dish dishData = findDishByName(dish.getName());
      if (dishData != null) {
        return null;
      }
      dish.setRestaurant(rest);
      return dishRepo.save(dish);

    }
  }

  public List<Dish> sortAllDishByNameForRestaurant(int restaurantId) {
//    Optional<Restaurant> data = restRepo.findById(restaurantId);
//    if (!data.isPresent()) {
//      return null;
//    }
//    Restaurant rest = data.get();
//    List<Dish> dishes = rest.getDishes();
//    dishes.sort(Dish.DishNameComparator);
    //int res = Integer.parseInt(restaurantId);
    List<Dish> dishes = dishRepo.findDishById(restaurantId);
    return dishes;
  }


  public void deleteDishForRestaurant(int dishId) {
    dishRepo.deleteById(dishId);
  }

  public Dish updateDishForRestaurant(int dishId, Dish newDish) {
    Optional<Dish> data = dishRepo.findById(dishId);
    if (data.isPresent()) {
      Dish dish = data.get();
      dish.setName(newDish.getName());
      dish.setPrice(newDish.getPrice());
      dish.setDescription(newDish.getDescription());
      return dishRepo.save(dish);
    }
    return null;
  }


  public Dish findDishByName(String name) {
    Optional<Dish> data = dishRepo.findDishByName(name);
    return data.orElse(null);
  }

  public Dish findDishByDishId(int id) {
    Dish data = dishRepo.findDishByDishId(id);
    return data;
  }



}
