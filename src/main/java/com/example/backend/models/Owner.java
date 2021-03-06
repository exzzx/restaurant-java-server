package com.example.backend.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "Owner")
public class Owner extends User {


//  @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL,
//          orphanRemoval = true)
  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Restaurant> restaurant;


  public Owner() {
    super("OWNER_USER");
  }

  public Owner(String username, String password, String first, String last, String email, String phone, String address,
               Date reg, String link, String userType) {
    super(username, password, first, last, email, phone, address, reg, link, userType);

  }

  public List<Restaurant> getRestaurant() {
    return restaurant;
  }

  public void setRestaurants(List<Restaurant> restaurant) {
    this.restaurant = restaurant;
  }


}
