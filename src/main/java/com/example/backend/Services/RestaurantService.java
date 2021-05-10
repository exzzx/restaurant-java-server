package com.example.backend.Services;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.backend.models.Owner;
import com.example.backend.models.Restaurant;
import com.example.backend.repositories.OwnerRepository;
import com.example.backend.repositories.RestaurantRepository;


@Service
public class RestaurantService {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Autowired
  OwnerRepository ownerRepo;

  @Autowired
  YelpAPIService yelpAPIService;


  public List<Restaurant> findAllRestaurants() {
    List<Restaurant> resultRestaurant = new ArrayList<>();
    List<Restaurant> allRestaurants = (List<Restaurant>) restaurantRepository.findAll();
    for (Restaurant restaurant : allRestaurants) {
      if (restaurant.getOwner() != null) {
        resultRestaurant.add(restaurant);
      }
    }
    return resultRestaurant;
  }


  public List<Restaurant> findRestaurantByCity(String city) throws IOException, JSONException {
    List<Restaurant> data = restaurantRepository.findRestaurantByCity(city);
    if (data.size() != 0) {
      return data;
    } else {
      return (List<Restaurant>) yelpAPIService.findRelativeBusinessesByCity(city);
    }
  }

  public Owner findOwnerForRestaurant(int resId) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(resId);
    if (restaurant.isPresent()) {
      return restaurant.get().getOwner();
    } else {
      return null;
    }

  }

  public Restaurant findRestaurantById(int resId) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(resId);
    if (restaurant.isPresent()) {
      return restaurant.get();
    } else {
      return null;
    }

  }


  public Restaurant createRestaurant(int ownerId, Restaurant restaurant) {

    Optional<Restaurant> data = restaurantRepository.findRestaurantByName(restaurant.getName());
    Optional<Owner> ownerData = ownerRepo.findById(ownerId);
    if (ownerData.isPresent()) {
      Owner owner = ownerData.get();
      if (data.isPresent()) {
        return null;
      } else {
        restaurant.setOwner(owner);
        return restaurantRepository.save(restaurant);
      }
    }
    return null;
  }

  public Restaurant createRestaurantForOwner(String ownerName, Restaurant restaurant) {
    Optional<Owner> data = ownerRepo.findUserByUsername(ownerName);
    if (data.isPresent()) {
      Owner existedOwner = data.get();
      if (existedOwner.getRestaurant() != null) {
        return null;
      } else {
        restaurant.setOwner(existedOwner);
        return restaurantRepository.save(restaurant);
      }
    } else {
      Owner owner = new Owner();
      owner.setUsername(ownerName);
      owner.setPassword(ownerName);
      Owner existedOwner = ownerRepo.save(owner);
      restaurant.setOwner(existedOwner);
      return restaurantRepository.save(restaurant);
    }
  }

  public Restaurant findRestaurantByYelpId(int id) throws IOException, JSONException {

    Optional<Restaurant> data = restaurantRepository.findById(id);
    if (data.isPresent()) {
      Restaurant existedRestaurant = data.get();
      if (existedRestaurant.getDescriptionPictures().size() != 0) {
        return existedRestaurant;
      } else {
        return yelpAPIService.findRestaurantDetailByYelpId(existedRestaurant.getYelpId());
      }
    } else {
      return null;
    }
  }

  public List<Restaurant> findRestaurantByOwner(int ownerId) {
    Optional<Owner> data = ownerRepo.findById(ownerId);

    if (data.isPresent()) {
      Owner restaurateur = data.get();
      return restaurateur.getRestaurant();
    }

    return null;
  }

  public Restaurant updateRestaurantById(Restaurant restaurant, Owner owner) throws IllegalArgumentException {
//    Optional<Restaurant> data = restaurantRepository.findById(restaurant.getId());

//    if (data.isPresent()) {
      Restaurant existedRestaurant = restaurant;
      existedRestaurant.setOwner(owner);
    if(restaurant==null){
      throw new IllegalArgumentException("restaurant is null !");
    }
    if(owner==null){
      throw new IllegalArgumentException("owner is null !");
    }

//      existedRestaurant.setName(restaurant.getName());
//      existedRestaurant.setCuisineType(restaurant.getCuisineType());
//      existedRestaurant.setPhotoLink(restaurant.getPhotoLink());
//      existedRestaurant.setPhone(restaurant.getPhone());
//      existedRestaurant.setAddress(restaurant.getAddress());
//      existedRestaurant.setCity(restaurant.getCity());
//      existedRestaurant.setState(restaurant.getState());
//      existedRestaurant.setPrice(restaurant.getPrice());
      return restaurantRepository.save(existedRestaurant);
//    } else {
//      return null;
//    }

  }

  public Restaurant updateRestaurantById(Restaurant restaurant) {
    Optional<Restaurant> data = restaurantRepository.findById(restaurant.getId());
    if (data.isPresent()) {
      Restaurant existedRestaurant = data.get();
      existedRestaurant.setName(restaurant.getName());
      existedRestaurant.setCuisineType(restaurant.getCuisineType());
      existedRestaurant.setPhotoLink(restaurant.getPhotoLink());
      existedRestaurant.setPhone(restaurant.getPhone());
      existedRestaurant.setAddress(restaurant.getAddress());
      existedRestaurant.setCity(restaurant.getCity());
      existedRestaurant.setState(restaurant.getState());
      existedRestaurant.setPrice(restaurant.getPrice());
      return restaurantRepository.save(existedRestaurant);
    } else {
      return null;
    }

  }

  public Restaurant adminUpdateRes(int resId, Restaurant restaurant) {
    Optional<Restaurant> data = restaurantRepository.findById(resId);
    if (data.isPresent()) {
      Restaurant existedRes = data.get();
      existedRes.setRating(restaurant.getRating());
      existedRes.setRatingAmount(restaurant.getRatingAmount());
      return restaurantRepository.save(existedRes);
    } else {
      return null;
    }

  }

  public void deleteRestaurantById(int resId) {
    Optional<Restaurant> data = restaurantRepository.findById(resId);
    if (data.isPresent()) {
      Restaurant existedRes = data.get();
      existedRes.setOwner(null);
      restaurantRepository.save(existedRes);
      restaurantRepository.deleteById(resId);
    }
  }
}
