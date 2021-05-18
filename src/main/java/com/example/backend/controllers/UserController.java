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


import javax.servlet.http.HttpSession;

import com.example.backend.Services.UserService;
import com.example.backend.models.Admin;
import com.example.backend.models.Customer;
import com.example.backend.models.Deliverer;
import com.example.backend.models.Owner;
import com.example.backend.models.User;


@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "http://yumhub-client.s3-website-us-east-1.amazonaws.com", allowCredentials = "true")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/api/admin/user")
  public List<User> findAllUsers() {
    return userService.findAllUsers();
  }

  @GetMapping("/api/user/customer")
  public List<Customer> findAllCustomers() {
    return userService.findAllCustomers();
  }

  @GetMapping("/api/deliverer")
  public List<Deliverer> findAllDeliverers() {
    return userService.findAllDeliverers();
  }

  @GetMapping("/api/user/owner")
  public List<Owner> findAllOwners() {
    return userService.findAllOwners();
  }

  @GetMapping("/api/user/{userId}")
  public User findUserById(@PathVariable("userId") int id) {
    return userService.findUserById(id);
  }

  @GetMapping("/api/currentUser")
  public User findCurrentUser(HttpSession session) {
    return userService.findCurrentUser(session);
  }

  // registers

  @PostMapping("/api/customer/signUp")
  public Customer customerRegister(@RequestBody Customer customer, HttpSession session) {
    return userService.customerRegister(customer, session);
  }

  @PostMapping("/api/owner/signUp")
  public Owner ownerRegister(@RequestBody Owner owner, HttpSession session) {
    return userService.ownerRegister(owner, session);
  }

  @PostMapping("/api/deliverer/signUp")
  public Deliverer delivererRegister(@RequestBody Deliverer deliverer, HttpSession session) {
    return userService.delivererRegister(deliverer, session);
  }


  @PostMapping("/api/admin/signUp")
  public Admin adminRegister(@RequestBody Admin admin, HttpSession session) {
    return userService.adminRegister(admin, session);
  }


  // logins
  @PostMapping("/api/customer/login")
  public Customer customerLogin(@RequestBody Customer credentials, HttpSession session) {
    return userService.customerLogin(credentials, session);
  }


  @PostMapping("/api/owner/logIn")
  public Owner ownerLogin(@RequestBody Owner credentials, HttpSession session) {
    return userService.ownerLogin(credentials, session);
  }


  @PostMapping("/api/deliverer/logIn")
  public Deliverer delivererLogin(@RequestBody Deliverer credentials, HttpSession session) {
    return userService.delivererLogin(credentials, session);
  }


  @PostMapping("/api/admin/logIn")
  public Admin adminLogin(@RequestBody Admin credentials, HttpSession session) {
    return userService.adminLogin(credentials, session);
  }


  @PostMapping("/api/currentUser/logOut")
  public void logout(HttpSession session) {
    userService.logout(session);
  }

  @PostMapping("/api/admin/create/user")
  public User adminCreateUser(@RequestBody User user) {
     return userService.adminCreateUser(user);
  }

  @PutMapping("/api/user/profile/update")
  public User userProfileUpdate(@RequestBody User user, HttpSession session) {
    return userService.userProfileUpdate(user, session);
  }

  @PutMapping("/api/deliverer/profile/update")
  public Deliverer delivererProfileUpdate(@RequestBody Deliverer deliverer, HttpSession session) {
    return userService.delivererProfileUpdate(deliverer, session);
  }


  @PutMapping("/api/admin/deliverer/update")
  public Deliverer adminUpdateDeliverer(@RequestBody Deliverer deliverer) {
    return userService.adminUpdateDeliverer(deliverer);
  }

  @PutMapping("/api/admin/user/update")
  public User adminUpdateUser(@RequestBody User user) {
    return userService.adminUpdateUser(user);

  }

  @DeleteMapping("/api/user/delete/{userId}")
  public void deleteUserById(@PathVariable("userId") int userId) {
    userService.deleteUserById(userId);
  }

}