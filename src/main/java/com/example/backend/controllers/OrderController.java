package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import com.example.backend.Services.OrderService;
import com.example.backend.models.Order;


@RestController
@CrossOrigin(origins = "http://3.131.152.182", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

  @Autowired
  OrderService orderService;

  @GetMapping("/api/customer/orders/{customerId}")
  public List<Order> findOrdersForCustomer(@PathVariable("customerId") int customerId) {
    return orderService.findOrdersForCustomer(customerId);
  }

  @GetMapping("/api/restaurant/{resId}/order")
  public List<Order> findOrdersForRestaurant(@PathVariable("resId") int restaurantId) {
    return orderService.findOrdersForRestaurant(restaurantId);
  }

  @GetMapping("/api/deliverer/{dId}/order")
  public List<Order> findOrdersForDeliverer(@PathVariable("dId") int deliverId) {
    return orderService.findOrdersForDeliverer(deliverId);
  }

  @GetMapping("/api/admin/orders")
  public List<Order> findAllOrdersForAdmin() {
    return orderService.findAllOrdersForAdmin();
  }


  @PutMapping("/api/deliverer/{dId}/order/{orderId}/confirm")
  public Order confirmReceiveAnOrder(@PathVariable("orderId") int orderId,
                                     @PathVariable("dId") int delivererId) {
    return orderService.confirmReceiveAnOrder(orderId, delivererId);

  }

  @PutMapping("/api/order/accept/{orderId}")
  public Order acceptOrder(@PathVariable("orderId") int orderId) {
    return orderService.acceptOrder(orderId);
  }

  @PutMapping("/api/order/{orderId}/deliverer/{delivererId}")
  public Order assignDelivererOrder(@PathVariable("orderId") int orderId,
                                    @PathVariable("delivererId") int delivererId) {
    return orderService.assignDelivererOrder(orderId, delivererId);
  }

  @PutMapping("/api/admin/order/{orderId}/{status}/{note}")
  public Order updateOrderById(@PathVariable("orderId") int orderId,
                               @PathVariable("status") String status, @PathVariable("note") String note) {
    return orderService.updateOrderById(orderId, status, note);
  }

  @DeleteMapping("/api/admin/order/{orderId}")
  public void deleteOrderById(@PathVariable("orderId") int orderId) {
    orderService.deleteOrderById(orderId);
  }
}