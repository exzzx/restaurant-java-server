package com.example.backend.controllers;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import com.example.backend.Services.YelpAPIService;
import com.example.backend.models.Restaurant;



@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class YelpAPIController {

  @Autowired
  YelpAPIService yelpAPIService;


  @GetMapping("/api/businessSearch/{city}")
  public Iterable<Restaurant> findRelativeBusinessesByCity(@PathVariable("city") String city)
          throws IOException, JSONException {
    return yelpAPIService.findRelativeBusinessesByCity(city);
  }

  @GetMapping("/api/businessDetail/{yelpId}")
  public Restaurant findRestaurantDetailByYelpId(@PathVariable("yelpId") String yelpId)
          throws IOException, JSONException {
    return yelpAPIService.findRestaurantDetailByYelpId(yelpId);
  }
}
