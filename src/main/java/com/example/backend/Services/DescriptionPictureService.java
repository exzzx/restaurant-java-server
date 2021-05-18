package com.example.backend.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.backend.models.DescriptionPicture;
import com.example.backend.models.Restaurant;
import com.example.backend.repositories.DescriptionPictureRepository;
import com.example.backend.repositories.RestaurantRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
//@CrossOrigin(origins = "*", allowCredentials = "true")
public class DescriptionPictureService {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Autowired
  DescriptionPictureRepository descriptionPictureRepository;

  public List<DescriptionPicture> findAllPicsForRes(int resId) {
    List<DescriptionPicture> allPics = (List<DescriptionPicture>) descriptionPictureRepository.findAll();
    List<DescriptionPicture> resultPics = new ArrayList<>();
    for (DescriptionPicture pic : allPics) {
      if (pic.getRestaurantPicture().getId() == resId) {
        resultPics.add(pic);
      }
    }
    return resultPics;

  }

  public DescriptionPicture createDishForRestaurant(int restaurantId, DescriptionPicture link) {
    Optional<Restaurant> data = restaurantRepository.findById(restaurantId);

    if (!data.isPresent()) {
      return null;
    } else {
      Restaurant rest = data.get();
      link.setRestaurantPicture(rest);
      return descriptionPictureRepository.save(link);
    }
  }

  public DescriptionPicture updateDishForRestaurant(int linkId, DescriptionPicture newLink) {
    Optional<DescriptionPicture> data = descriptionPictureRepository.findById(linkId);
    if (data.isPresent()) {
      DescriptionPicture existedPic = data.get();
      existedPic.setLink(newLink.getLink());
      return descriptionPictureRepository.save(existedPic);
    }
    return null;
  }

  public void deleteDishForRestaurant(int linkId) {
    descriptionPictureRepository.deleteById(linkId);
  }

}
