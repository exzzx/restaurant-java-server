package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


import com.example.backend.Services.ShoppingCartItemService;
import com.example.backend.models.ShoppingCartItem;


@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class ShoppingCartItemController {

  @Autowired
  ShoppingCartItemService shoppingCartItemService;

  @PostMapping("/api/shoppingCart/{shoppingCartId}/dish/{dishId}")
  public ShoppingCartItem addDishToShoppingCart(
          @PathVariable("shoppingCartId") int shoppingCartId, @PathVariable("dishId") int dishId) {
    return shoppingCartItemService.addDishToShoppingCart(shoppingCartId, dishId);
  }

  @GetMapping("/api/shoppingCart/{shoppingCartId}")
  public List<ShoppingCartItem> getAllItemsInCart(
          @PathVariable("shoppingCartId") int shoppingCartId) {
    return shoppingCartItemService.getAllItemsInCart(shoppingCartId);
  }

  @PutMapping("/api/shoppingCartItem/{shoppingCartItemId}/{quantity}")
  public void updateItemQuantityPrice(
          @PathVariable("shoppingCartItemId") int shoppingCartItemId,
          @PathVariable("quantity") int quantity) {
    shoppingCartItemService.updateItemQuantityPrice(shoppingCartItemId, quantity);
  }

  @DeleteMapping("/api/shoppingCartItem/{shoppingCartItemId}")
  public void deleteItemById(@PathVariable("shoppingCartItemId") int shoppingCartItemId) {
    shoppingCartItemService.deleteItemById(shoppingCartItemId);
  }
}