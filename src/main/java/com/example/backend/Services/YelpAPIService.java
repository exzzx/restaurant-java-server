package com.example.backend.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.backend.models.DescriptionPicture;
import com.example.backend.models.Restaurant;
import com.example.backend.repositories.DescriptionPictureRepository;
import com.example.backend.repositories.RestaurantRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;


@Service
public class YelpAPIService {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Autowired
  DescriptionPictureRepository descriptionPictureRepository;

  private String accessKey = "OCl_ro6mOdFhGhAH9PE9hPT1PJ5oB5R8jMORNSERElbeZKfbDTXKKzpvJxN7Hzc6Q7yJfr5yQL01efp873R0Re-pkLO_yVj3w4-uBdAeUY7JXQ8AHBN9i8jLFHhxXnYx";
  private OkHttpClient client = new OkHttpClient();


  public Iterable<Restaurant> findRelativeBusinessesByCity(String city)
          throws IOException, JSONException {

    Request request2 = new Builder()
            .url("https://api.yelp.com/v3/businesses/search?limit=30&radius=2000" + "&location="
                    + city)
            .get()
            .addHeader("authorization", "Bearer" + " " + accessKey).build();

    Response response2 = client.newCall(request2).execute();
    JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response2.body()).string().trim());       // parser
    JSONArray myResponse = (JSONArray) jsonObject.get("businesses");
    return restaurantRepository.saveAll(jsonArrayToRestaurantList(myResponse));
  }

  public Restaurant findRestaurantDetailByYelpId(String yelpId)
          throws IOException, JSONException {
    Request request = new Builder()
            .url("https://api.yelp.com/v3/businesses/" + yelpId)
            .get()
            .addHeader("authorization", "Bearer" + " " + accessKey).build();
    Response response = client.newCall(request).execute();
    JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string().trim());
    Restaurant rawData = jsonToRestaurant(jsonObject);
    Optional<Restaurant> data = restaurantRepository.findRestaurantByYelpId(rawData.getYelpId());
    if (data.isPresent()) {
      // Add description pictures to the existed restaurant
      Restaurant existedRestaurant = data.get();

      for (DescriptionPicture picture : rawData.getDescriptionPictures()) {
        DescriptionPicture newPicture = new DescriptionPicture();
        newPicture.setLink(picture.getLink());
        newPicture.setRestaurantPicture(existedRestaurant);
        descriptionPictureRepository.save(newPicture);
        existedRestaurant.addDescriptionPicture(newPicture);
      }
      return restaurantRepository.save(existedRestaurant);
    } else {
      return null;
    }
  }


  private List<Restaurant> jsonArrayToRestaurantList(JSONArray response) throws JSONException {
    List<Restaurant> restaurants = new ArrayList<>();
    for (int i = 0; i < response.length(); i++) {
      JSONObject temp = response.getJSONObject(i);
      Restaurant restaurant = jsonToRestaurant(temp);
      restaurants.add(restaurant);
    }
    return restaurants;
  }


  private Restaurant jsonToRestaurant(JSONObject object) throws JSONException {
    Restaurant res = new Restaurant();

    res.setName(object.getString("name"));
    res.setYelpId(object.getString("id"));
    try {
      res.setPhone(object.getString("display_phone"));
    } catch (Exception e) {
      res.setPhone(object.getString("phone"));
    }
    res.setCity(object.getJSONObject("location").getString("city"));
    res.setState(object.getJSONObject("location").getString("state"));
    res.setAddress(object.getJSONObject("location").getString("address1"));
    res.setPhotoLink(object.getString("image_url"));
    res.setRating(object.getDouble("rating"));
    try {
      res.setPrice(object.getString("price"));
    } catch (Exception e) {
      res.setPrice(null);
    }
    try {
      res.setDistance(object.getDouble("distance"));
    } catch (Exception e) {
      res.setDistance(null);
    }
    JSONArray categories = object.getJSONArray("categories");
    StringBuilder category = new StringBuilder();
    for (int j = 0; j < categories.length(); j++) {
      category.append(categories.getJSONObject(j).getString("title")).append(", ");
    }
    res.setCuisineType(category.substring(0, category.length() - 2));

    try {
      JSONArray photos = object.getJSONArray("photos");
      List<DescriptionPicture> pictures = new ArrayList<>();
      for (int i = 0; i < photos.length(); i++) {
        DescriptionPicture picture = new DescriptionPicture();
        picture.setLink(photos.getString(i));
        pictures.add(picture);
      }
      res.setDescriptionPictures(pictures);
    } catch (Exception e) {
      res.setDescriptionPictures(null);
    }
    //res.setCuisineType(object.getJSONObject("categories").getString("alias"));
    return res;

  }


}