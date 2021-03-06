package com.example.backend.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.backend.models.Customer;
import com.example.backend.models.Dish;
import com.example.backend.models.ShoppingCart;
import com.example.backend.models.ShoppingCartItem;
import com.example.backend.repositories.CustomerRepository;
import com.example.backend.repositories.DishRepository;
import com.example.backend.repositories.ShoppingCartItemRepository;
import com.example.backend.repositories.ShoppingCartRepository;

@Service
//@CrossOrigin(origins = "*", allowCredentials = "true")
public class ShoppingCartItemService {

  @Autowired
  ShoppingCartRepository shoppingCartRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  DishRepository dishRepository;

  @Autowired
  ShoppingCartItemRepository shoppingCartItemRepository;

  @Autowired
  ShoppingCartService shoppingCartService;

  @PostMapping("/api/shoppingCart/{shoppingCartId}/dish/{dishId}")
  public ShoppingCartItem addDishToShoppingCart(
          @PathVariable("shoppingCartId") int shoppingCartId, @PathVariable("dishId") int dishId) {
    Optional<ShoppingCart> data = shoppingCartRepository.findById(shoppingCartId);
    Optional<Dish> dish = dishRepository.findById(dishId);
    List<ShoppingCartItem> allShoppingCartItems =
            (List<ShoppingCartItem>) shoppingCartItemRepository.findAll();
    boolean existed = false;
    for (ShoppingCartItem item : allShoppingCartItems) {
      if (item.getDish().getId() == dishId && item.getCart().getId() == shoppingCartId) {
        existed = true;
      }
    }
    if (existed) {
      return null;
    } else {
      if (!data.isPresent()) {
        ShoppingCart newShoppingCart = createShoppingCart(shoppingCartId);
        ShoppingCartItem newShoppingCartItem = getShoppingCartItem(dish, newShoppingCart);
        if (newShoppingCartItem != null) return newShoppingCartItem;
        return null;
      } else {
        ShoppingCart existedShoppingCart = data.get();
        ShoppingCartItem newShoppingCartItem = getShoppingCartItem(dish, existedShoppingCart);
        if (newShoppingCartItem != null) return newShoppingCartItem;
        return null;
      }
    }
  }

  @GetMapping("/api/shoppingCart/{shoppingCartId}")
  public List<ShoppingCartItem> getAllItemsInCart(
          @PathVariable("shoppingCartId") int shoppingCartId) {
    List<ShoppingCartItem> allShoppingCartItems =
            (List<ShoppingCartItem>) shoppingCartItemRepository.findAll();
    List<ShoppingCartItem> resultShoppingCartItems = new ArrayList<>();
    for (ShoppingCartItem result : allShoppingCartItems) {
      if (result.getCart().getId() == shoppingCartId) {
        resultShoppingCartItems.add(result);
      }
    }
    return resultShoppingCartItems;

  }

  @PutMapping("/api/shoppingCartItem/{shoppingCartItemId}/{quantity}")
  public void updateItemQuantityPrice(
          @PathVariable("shoppingCartItemId") int shoppingCartItemId,
          @PathVariable("quantity") int quantity) {
    Optional<ShoppingCartItem> shoppingCartItem = shoppingCartItemRepository.findById(shoppingCartItemId);
    if (shoppingCartItem.isPresent()) {
      ShoppingCartItem existedShoppingCartItem = shoppingCartItem.get();
      existedShoppingCartItem.setQuantity(quantity);
      existedShoppingCartItem.setItemPrice(quantity * existedShoppingCartItem.getDish().getPrice());
      shoppingCartItemRepository.save(existedShoppingCartItem);
    }

  }

  @DeleteMapping("/api/shoppingCartItem/{shoppingCartItemId}")
  public void deleteItemById(@PathVariable("shoppingCartItemId") int shoppingCartItemId) {
    shoppingCartItemRepository.deleteById(shoppingCartItemId);
  }


  private ShoppingCartItem getShoppingCartItem(Optional<Dish> dish, ShoppingCart existedShoppingCart) {
    if (dish.isPresent()) {
      ShoppingCartItem newShoppingCartItem = new ShoppingCartItem();
      newShoppingCartItem.setCart(existedShoppingCart);
      newShoppingCartItem.setDish(dish.get());
      newShoppingCartItem.setItemPrice(dish.get().getPrice());
      newShoppingCartItem.setDishName(dish.get().getName());
      return shoppingCartItemRepository.save(newShoppingCartItem);
    }
    return null;
  }


  private ShoppingCart createShoppingCart(int customerId) {
    Optional<Customer> data = customerRepository.findById(customerId);
    if (data.isPresent()) {
      Customer customer = data.get();
      ShoppingCart newShoppingCart = new ShoppingCart();
      newShoppingCart.setCustomer(customer);
      return shoppingCartRepository.save(newShoppingCart);
    }
    return null;
  }

}
