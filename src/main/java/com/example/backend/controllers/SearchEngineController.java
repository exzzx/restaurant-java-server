package com.example.backend.controllers;
//mvn spring-boot:run
import com.example.backend.Services.SearchEngineService;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@CrossOrigin(origins = "http://3.131.152.182", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*")
public class SearchEngineController {

//  @Autowired
//  SearchEngineService searchEngineService;
//
//  @GetMapping("/hello")
//  public String helloworld() {
//    return "hello!";
//  }
//
//  @GetMapping("/api/restaurant/{city}")
//  public String findRestaurantsByCity(@PathVariable("city") String city) throws IOException, JSONException {
//    System.out.println(searchEngineService.findRestaurantsByCity(city));
//    return searchEngineService.findRestaurantsByCity(city);
//  }
}
