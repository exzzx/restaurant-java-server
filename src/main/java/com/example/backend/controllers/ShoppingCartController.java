package com.example.backend.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.example.backend.Services.ShoppingCartService;
import com.example.backend.models.ShoppingCart;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@CrossOrigin(origins = "http://3.131.152.182", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ShoppingCartController {


  @Autowired
  ShoppingCartService shoppingCartService;


  @GetMapping("/api/shoppingCart/price/{shoppingCartId}")
  public ShoppingCart getPriceForShoppingCart(@PathVariable("shoppingCartId") int cartId) {
    return shoppingCartService.getPriceForShoppingCart(cartId);
  }


  @DeleteMapping("/api/shoppingcart/{cartId}")
  public void deleteShoppingCart(@PathVariable("cartId") int cartId) {
    shoppingCartService.deleteShoppingCart(cartId);
  }


}
