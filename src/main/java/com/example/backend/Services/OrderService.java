package com.example.backend.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.backend.models.Deliverer;
import com.example.backend.models.Order;
import com.example.backend.models.OrderStatus;
import com.example.backend.repositories.DelivererRepository;
import com.example.backend.repositories.OrderRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import static com.example.backend.models.OrderStatus.DELIVERED;
import static com.example.backend.models.OrderStatus.HOLD;
import static com.example.backend.models.OrderStatus.SHIPPED;

@Service
//@CrossOrigin(origins = "*", allowCredentials = "true")
public class OrderService {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  DelivererRepository delivererRepository;

  public List<Order> findOrdersForCustomer(int customerId) {
    List<Order> allOrders = (List<Order>) orderRepository.findAll();
    List<Order> resultOrders = new ArrayList<>();
    for (Order order : allOrders) {
      if (order.getCustomer() != null) {
        if (order.getCustomer().getId() == customerId) {
          resultOrders.add(order);
        }
      }
    }
    return resultOrders;
  }

  public List<Order> findOrdersForRestaurant(int restaurantId) {
    List<Order> allOrders = (List<Order>) orderRepository.findAll();
    List<Order> resultOrders = new ArrayList<>();
    for (Order order : allOrders) {
      if (order.getRestaurant() != null) {
        if (order.getRestaurant().getId() == restaurantId) {
          resultOrders.add(order);
        }
      }
    }
    return resultOrders;
  }

  public List<Order> findOrdersForDeliverer(int deliverId) {
    List<Order> allOrders = (List<Order>) orderRepository.findAll();
    List<Order> resultOrders = new ArrayList<>();
    for (Order order : allOrders) {
      if (order.getDeliver() != null) {
        if (order.getDeliver().getId() == deliverId) {
          resultOrders.add(order);
        }
      }
    }
    return resultOrders;
  }

  public List<Order> findAllOrdersForAdmin() {
    return (List<Order>) orderRepository.findAll();
  }


  public Order confirmReceiveAnOrder(int orderId, int delivererId) {
    Optional<Order> data = orderRepository.findById(orderId);
    Optional<Deliverer> deliverData = delivererRepository.findById(delivererId);
    if (data.isPresent() && deliverData.isPresent()) {
      Order existedOrder = data.get();
      Deliverer existedDeliverer = deliverData.get();
      existedOrder.setStatus(DELIVERED);
      existedDeliverer.setIsFree(true);
      delivererRepository.save(existedDeliverer);
      return orderRepository.save(existedOrder);
    } else {
      return null;
    }

  }


  public Order acceptOrder(int orderId) {
    Optional<Order> data = orderRepository.findById(orderId);
    if (data.isPresent()) {
      Order existedOrder = data.get();
      existedOrder.setStatus(HOLD);
      return orderRepository.save(existedOrder);
    } else {
      return null;
    }

  }


  public Order assignDelivererOrder(int orderId, int delivererId) {
    Optional<Order> orderData = orderRepository.findById(orderId);
    Optional<Deliverer> delivererData = delivererRepository.findById(delivererId);
    if (orderData.isPresent() && delivererData.isPresent()) {
      Order existedOrder = orderData.get();
      Deliverer existedDeliverer = delivererData.get();
      existedOrder.setDeliver(existedDeliverer);
      existedOrder.setStatus(SHIPPED);
      existedDeliverer.setIsFree(false);
      delivererRepository.save(existedDeliverer);
      return orderRepository.save(existedOrder);
    } else {
      return null;
    }

  }


  public Order updateOrderById(int orderId, String status, String note) {
    Optional<Order> data = orderRepository.findById(orderId);
    if (data.isPresent()) {
      Order existedOrder = data.get();
      switch (status) {
        case "NEW":
          existedOrder.setStatus(OrderStatus.NEW);
          break;
        case "HOLD":
          existedOrder.setStatus(OrderStatus.HOLD);
          break;
        case "SHIPPED":
          existedOrder.setStatus(OrderStatus.SHIPPED);
          break;
        case "DELIVERED":
          existedOrder.setStatus(OrderStatus.DELIVERED);
      }
      existedOrder.setNote(note);
      return orderRepository.save(existedOrder);
    } else {
      return null;
    }


  }

  public void deleteOrderById(int orderId) {
    orderRepository.deleteById(orderId);
  }


}
