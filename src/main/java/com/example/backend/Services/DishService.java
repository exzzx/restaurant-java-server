package com.example.backend.Services;



import com.example.backend.Services.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private CacheService cacheService;

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
    String curKey = "restaurant_"+restaurantId;
    List<Dish> dishes = null;
    dishes = (List<Dish>) cacheService.getFromCommonCache(curKey);
    if (dishes == null) {
      dishes = (List<Dish>) redisTemplate.opsForList().range(curKey, 0, -1);
      if (dishes == null || dishes.isEmpty()) {
        dishes = dishRepo.findDishById(restaurantId);
        Long result = redisTemplate.opsForList().rightPushAll(curKey, dishes);
        redisTemplate.expire(curKey, 10, TimeUnit.MINUTES);
        // if result == null, it failed.
      }
      cacheService.setCommonCache(curKey, dishes);
    }

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
