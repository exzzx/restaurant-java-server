package com.example.backend.controllers;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.Services.RestaurantService;
import com.example.backend.Services.UserService;
import com.example.backend.models.Owner;
import com.example.backend.models.Restaurant;


@RestController
@CrossOrigin(origins = "http://3.131.152.182", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class RestaurantController {

  @Autowired
  RestaurantService restaurantService;

  @Autowired
  UserService userService;

  @GetMapping("/api/restaurant")
  public List<Restaurant> findAllRestaurants() {
    return restaurantService.findAllRestaurants();
  }


  @GetMapping("/api/restaurant/{city}")
  public List<Restaurant> findRestaurantByCity(@PathVariable("city") String city) throws IOException, JSONException {
    return restaurantService.findRestaurantByCity(city);
  }

  @GetMapping("/api/restaurant/{city}/10")
  public List<Restaurant> findRestaurantByCityLimit10(@PathVariable("city") String city) throws IOException, JSONException {
    List<Restaurant> buf = restaurantService.findRestaurantByCity(city);
    List<Restaurant> res = new ArrayList<>();
    for(int i=0; i<10; i++){
      res.add(buf.get(i));
    }
    return res;
  }

  @GetMapping("/api/restaurant/{resId}/owner")
  public Owner findOwnerForRestaurant(@PathVariable("resId") int resId) {
    return restaurantService.findOwnerForRestaurant(resId);
  }


  @PostMapping("/api/restaurant/owner/{ownerId}")
  public Restaurant createRestaurant(@PathVariable("ownerId") int ownerId,
                                     @RequestBody Restaurant restaurant) {

    return restaurantService.createRestaurant(ownerId, restaurant);
  }

  @PostMapping("/api/admin/restaurant/owner/{ownerName}")
  public Restaurant createRestaurantForOwner(@PathVariable("ownerName") String ownerName,
                                             @RequestBody Restaurant restaurant) {
    return restaurant.getOwner()==null?restaurantService.createRestaurantForOwner(ownerName, restaurant):restaurant;
  }

  @GetMapping("/api/restaurant/detail/{Id}")
  public Restaurant findRestaurantByYelpId(@PathVariable("Id") int id) throws IOException, JSONException {
    return restaurantService.findRestaurantByYelpId(id);
  }

  @GetMapping("/api/restaurant/owner/{ownerId}")
  public List<Restaurant> findRestaurantByOwner(@PathVariable("ownerId") int ownerId
  ) {
    return restaurantService.findRestaurantByOwner(ownerId);
  }

  @PutMapping("/api/restaurant/info/update")
  public Restaurant updateRestaurantById(@RequestBody Restaurant restaurant) {
    return restaurantService.updateRestaurantById(restaurant);
  }

  @PutMapping("/api/restaurant/info/update/{ownerId}")
  public Restaurant updateRestaurantById(@PathVariable("ownerId") int ownerId, @RequestBody Restaurant restaurant) {
    Owner owner = (Owner)userService.findUserById(ownerId);

    return restaurant.getOwner()!=null
            && restaurant.getOwner().getId() == ownerId
            ?restaurantService.updateRestaurantById(restaurant, owner):restaurant;
  }

  @PutMapping("/api/restaurant/info/update/{ownerId}/{resId}")
  public Restaurant updateRestaurantById(@PathVariable("ownerId") int ownerId, @PathVariable int resId) {
    Owner owner = (Owner)userService.findUserById(ownerId);
    Restaurant restaurant = restaurantService.findRestaurantById(resId);
    return restaurantService.updateRestaurantById(restaurant, owner);
  }


  @PutMapping("/api/admin/restaurant/{resId}")
  public Restaurant adminUpdateRes(@PathVariable("resId") int resId,
                                   @RequestBody Restaurant restaurant) {
    return restaurantService.adminUpdateRes(resId, restaurant);

  }

  @DeleteMapping("/api/restaurant/{resId}")
  public void deleteRestaurantById(@PathVariable("resId") int resId) {
    restaurantService.deleteRestaurantById(resId);
  }
}
