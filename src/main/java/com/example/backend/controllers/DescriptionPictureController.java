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

import com.example.backend.Services.DescriptionPictureService;
import com.example.backend.models.DescriptionPicture;

@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DescriptionPictureController {

  @Autowired
  DescriptionPictureService descriptionPictureService;

  @GetMapping("/api/res/{resId}/pic")
  public List<DescriptionPicture> findAllPicsForRes(@PathVariable("resId") int resId) {
    return descriptionPictureService.findAllPicsForRes(resId);
  }

  @PostMapping("/api/restaurant/{resId}/link")
  public DescriptionPicture createDishForRestaurant(@PathVariable("resId") int restaurantId,
                                                    @RequestBody DescriptionPicture link) {
   return descriptionPictureService.createDishForRestaurant(restaurantId, link);
  }

  @PutMapping("/api/picLink/{linkId}")
  public DescriptionPicture updateDishForRestaurant(@PathVariable("linkId") int linkId,
                                                    @RequestBody DescriptionPicture newLink) {
    return descriptionPictureService.updateDishForRestaurant(linkId, newLink);
  }

  @DeleteMapping("/api/picLink/{linkId}")
  public void deleteDishForRestaurant(@PathVariable("linkId") int linkId) {
    descriptionPictureService.deleteDishForRestaurant(linkId);
  }
}
