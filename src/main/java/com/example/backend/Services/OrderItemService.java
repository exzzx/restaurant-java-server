package com.example.backend.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.backend.models.Customer;
import com.example.backend.models.Order;
import com.example.backend.models.OrderItem;
import com.example.backend.models.OrderStatus;
import com.example.backend.models.Restaurant;
import com.example.backend.models.ShoppingCartItem;
import com.example.backend.repositories.CustomerRepository;
import com.example.backend.repositories.OrderItemRepository;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.ShoppingCartItemRepository;

@Service
public class OrderItemService {

  @Autowired
  ShoppingCartItemRepository shoppingCartItemRepository;

  @Autowired
  OrderItemRepository orderItemRepository;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  CustomerRepository customerRepository;

  public void placeOrder(int shoppingCartId, String note) {
    List<ShoppingCartItem> thisCartItems = new ArrayList<>();
    List<ShoppingCartItem> allCartItems = (List<ShoppingCartItem>) shoppingCartItemRepository.findAll();
    for (ShoppingCartItem item : allCartItems) {
      if (item.getCart().getId() == shoppingCartId) {
        thisCartItems.add(item);
      }
    }
    Restaurant orderRestaurant = thisCartItems.get(0).getDish().getRestaurant();
    Order newOrder = orderRepository.save(new Order());
    Optional<Customer> customer = customerRepository.findById(shoppingCartId);
    if (customer.isPresent()) {
      Customer existedCustomer = customer.get();
      // new Order
      newOrder.setCustomer(existedCustomer);
      newOrder.setStatus(OrderStatus.NEW);
      newOrder.setNote(note);
      newOrder.setRestaurant(orderRestaurant);
      orderRepository.save(newOrder);
      // new OrderItems
      List<OrderItem> orderItems = new ArrayList<>();
      for (ShoppingCartItem item : thisCartItems) {
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setOrder(newOrder);
        newOrderItem.setDish(item.getDish());
        newOrderItem.setDishName(item.getDishName());
        newOrderItem.setItemPrice(item.getItemPrice());
        newOrderItem.setQuantity(item.getQuantity());
        orderItems.add(newOrderItem);
        orderItemRepository.saveAll(orderItems);
        shoppingCartItemRepository.deleteAll(thisCartItems);

      }

    }


  }

}
