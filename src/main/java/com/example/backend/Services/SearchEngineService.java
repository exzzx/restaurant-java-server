package com.example.backend.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

@Service
public class SearchEngineService {

//  private String apiKey = "OCl_ro6mOdFhGhAH9PE9hPT1PJ5oB5R8jMORNSERElbeZKfbDTXKKzpvJxN7Hzc6Q7yJfr5yQL01efp873R0Re-pkLO_yVj3w4-uBdAeUY7JXQ8AHBN9i8jLFHhxXnYx";
//  private OkHttpClient client = new OkHttpClient();
//
//
//  public String findRestaurantsByCity(String city) throws IOException, JSONException {
//    Request request2 = new Builder()
//            .url("https://api.yelp.com/v3/businesses/search?limit=30&radius=2000" + "&location="
//                    + city)
//            .get()
//            .addHeader("authorization", "Bearer" + " " + apiKey).build();
//
//    Response response2 = client.newCall(request2).execute();
//
//    JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response2.body()).string().trim());       // parser
//    JSONArray myResponse = (JSONArray) jsonObject.get("businesses");
//
//    return myResponse.toString();
//    //return restaurantRepository.saveAll(jsonArrayToRestaurantList(myResponse));
//  }
}
