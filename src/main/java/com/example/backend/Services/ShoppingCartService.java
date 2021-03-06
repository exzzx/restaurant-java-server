package com.example.backend.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.backend.models.ShoppingCart;
import com.example.backend.models.ShoppingCartItem;
import com.example.backend.repositories.CustomerRepository;
import com.example.backend.repositories.ShoppingCartItemRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
//@CrossOrigin(origins = "*", allowCredentials = "true")
public class ShoppingCartService {


  @Autowired
  ShoppingCartRepository shopRepo;

  @Autowired

  CustomerRepository customerRepository;

  @Autowired
  ShoppingCartItemRepository shoppingCartItemRepository;

  public ShoppingCart getPriceForShoppingCart(int cartId) {
    ShoppingCart shoppingCart = shopRepo.findById(cartId).orElse(null);
    ShoppingCart existedShoppingCart = shoppingCart;
    if ( existedShoppingCart == null ) {
//    if (shoppingCart.isPresent()) {
//      ShoppingCart existedShoppingCart = shoppingCart.get();
      Iterable<ShoppingCartItem> existedItems = shoppingCartItemRepository.findAll();
      double totalPrice = 0;
      for (ShoppingCartItem item : existedItems) {
        if (item.getCart().getId() == cartId) {
          totalPrice = totalPrice + item.getItemPrice();
        }
      }
      existedShoppingCart.setTotalPrice(totalPrice);
      return shopRepo.save(existedShoppingCart);
    } else {
      return null;
    }

  }

  public void deleteShoppingCart(int cartId) {
    shopRepo.deleteById(cartId);
  }


}
