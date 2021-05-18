package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Services.OrderItemService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderItemController {

  @Autowired
  OrderItemService orderItemService;

  @PostMapping("/api/shoppingCart/{shoppingCartId}")
  public void placeOrder(@PathVariable("shoppingCartId") int shoppingCartId,
                         @RequestBody String note) {
    orderItemService.placeOrder(shoppingCartId, note);
  }

}