package com.example.backend.Services;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.example.backend.models.Admin;
import com.example.backend.models.Customer;
import com.example.backend.models.Deliverer;
import com.example.backend.models.Owner;
import com.example.backend.models.User;
import com.example.backend.repositories.AdminRepository;
import com.example.backend.repositories.CustomerRepository;
import com.example.backend.repositories.DelivererRepository;
import com.example.backend.repositories.OwnerRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import com.example.backend.repositories.UserRepository;

@Service
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "*", allowCredentials = "true")
public class UserService {

  @Autowired
  UserRepository repository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  OwnerRepository ownerRepository;

  @Autowired
  ShoppingCartRepository shoppingCartRepository;

  @Autowired
  DelivererRepository delivererRepository;

  @Autowired
  AdminRepository adminRepository;

//  @Autowired
//  private RedisTemplate redisTemplate;

  public List<User> findAllUsers() {
    return (List<User>) repository.findAll();
  }

  public List<Customer> findAllCustomers() {
    return (List<Customer>) customerRepository.findAll();
  }


  public List<Deliverer> findAllDeliverers() {
    return (List<Deliverer>) delivererRepository.findAll();
  }

  public List<Owner> findAllOwners() {
    return (List<Owner>) ownerRepository.findAll();
  }

  public User findUserById(int id) {
    Optional<User> data = repository.findById(id);
    return data.orElse(null);
  }

  public User findCurrentUser(HttpSession session, JSONObject body) {
    String uuidToken = UUID.randomUUID().toString();

    User currentUser = new User();
    try{
      currentUser.setUsername(body.getString("username"));
      currentUser.setPassword(body.getString("password"));
      currentUser.setUserType(body.getString("userType"));
    } catch (Exception exception) {
      System.out.println(exception);
    }

    if (currentUser == null) {
      return null;
    } else {
      Optional<User> data = repository.findUserByUsername(currentUser.getUsername());


      return data.orElse(null);
    }
  }

  // registers
  public Customer customerRegister(Customer customer, HttpSession session) {
    String username = customer.getUsername();

    Optional<User> data = repository.findUserByUsername(username);
    if (!data.isPresent()) {
      Customer newCustomer = new Customer();
      newCustomer.setUsername(customer.getUsername());
      newCustomer.setPassword(customer.getPassword());
      // ShoppingCart newCart = new ShoppingCart();

      Customer savedCustomer = repository.save(newCustomer);
      session.setAttribute("currentUser", savedCustomer);
      // newCart.setCustomer(savedCustomer);
      //shoppingCartRepository.save(newCart);

      return savedCustomer;
    }

    return null;
  }


  public Owner ownerRegister(Owner owner, HttpSession session) {

    String username = owner.getUsername();
    Optional<User> data = repository.findUserByUsername(username);
    if (!data.isPresent()) {
      Owner existedOwner = ownerRepository.save(owner);
      session.setAttribute("currentUser", existedOwner);
      return existedOwner;
    }

    return null;
  }

  public Deliverer delivererRegister(Deliverer deliverer, HttpSession session) {
    String username = deliverer.getUsername();
    Optional<User> data = repository.findUserByUsername(username);
    if (!data.isPresent()) {
      Deliverer existedDeliverer = delivererRepository.save(deliverer);
      session.setAttribute("currentUser", existedDeliverer);
      return existedDeliverer;
    }
    return null;
  }


  public Admin adminRegister(Admin admin, HttpSession session) {
    String username = admin.getUsername();


    Optional<User> data = repository.findUserByUsername(username);
    if (!data.isPresent()) {
      Admin existedAdmin = adminRepository.save(admin);
      session.setAttribute("currentUser", existedAdmin);
      return existedAdmin;
    }

    return null;
  }


  // logins
  public Customer customerLogin(Customer credentials, HttpSession session) {
    String username = credentials.getUsername();
    String password = credentials.getPassword();
    Optional<Customer> result = customerRepository.findUserByCredentials(username, password);

    if (result.isPresent()) {
      Customer customer = result.get();
      customer.setFirstName("Someone");
      customer.setLastName("here");
      session.setAttribute("currentUser", customer);
      return customer;
    }

    return null;

  }


  public Owner ownerLogin(Owner credentials, HttpSession session) {
    String username = credentials.getUsername();
    String password = credentials.getPassword();

    Optional<Owner> result = ownerRepository.findUserByCredentials(username, password);

    if (result.isPresent()) {
      Owner owner = result.get();
      session.setAttribute("currentUser", owner);
      return owner;
    }

    return null;
  }

  public Deliverer delivererLogin(Deliverer credentials, HttpSession session) {
    String username = credentials.getUsername();
    String password = credentials.getPassword();

    Optional<Deliverer> result = delivererRepository.findDelivererByCredentials(username, password);

    if (result.isPresent()) {
      Deliverer deliverer = result.get();

      session.setAttribute("currentUser", deliverer);
      return deliverer;
    }

    return null;
  }


  public Admin adminLogin(Admin credentials, HttpSession session) {
    String username = credentials.getUsername();
    String password = credentials.getPassword();

    Optional<Admin> result = adminRepository.findUserByCredentials(username, password);
    if (result.isPresent()) {
      Admin admin = result.get();
      session.setAttribute("currentUser", admin);
      return admin;
    }

    return null;
  }


  public void logout(HttpSession session) {
    session.invalidate();
  }


  public User adminCreateUser(User user) {
    switch (user.getUserType()) {
      case "CUSTOMER_USER":
        Customer customer = new Customer();
        customer.setPassword(user.getPassword());
        customer.setUsername(user.getUsername());
        return customerRepository.save(customer);
      case "OWNER_USER":
        Owner owner = new Owner();
        owner.setPassword(user.getPassword());
        owner.setUsername(user.getUsername());
        return ownerRepository.save(owner);
      case "DELIVERER_USER":
        Deliverer deliverer = new Deliverer();
        deliverer.setPassword(user.getPassword());
        deliverer.setUsername(user.getUsername());
        return delivererRepository.save(deliverer);
      default:
        return null;
    }
  }

  public User userProfileUpdate(User user, HttpSession session) {
    Optional<User> data = repository.findById(user.getId());
    if (data.isPresent()) {
      User existedUser = data.get();
      existedUser.setUsername(user.getUsername());
      existedUser.setEmail(user.getEmail());
      existedUser.setPassword(user.getPassword());
      existedUser.setAddress(user.getAddress());
      existedUser.setPhone(user.getPhone());
      session.setAttribute("currentUser", existedUser);
      return repository.save(existedUser);
    } else {
      return null;
    }

  }

  public Deliverer delivererProfileUpdate(Deliverer deliverer, HttpSession session) {
    Optional<Deliverer> data = delivererRepository.findById(deliverer.getId());
    if (data.isPresent()) {
      Deliverer existedUser = data.get();
      existedUser.setUsername(deliverer.getUsername());
      existedUser.setEmail(deliverer.getEmail());
      existedUser.setPassword(deliverer.getPassword());
      existedUser.setAddress(deliverer.getAddress());
      existedUser.setPhone(deliverer.getPhone());
      existedUser.setCarPlate(deliverer.getCarPlate());
      session.setAttribute("currentUser", existedUser);
      return delivererRepository.save(existedUser);
    } else {
      return null;
    }

  }


  public Deliverer adminUpdateDeliverer(Deliverer deliverer) {
    Optional<Deliverer> data = delivererRepository.findById(deliverer.getId());
    if (data.isPresent()) {
      Deliverer existedUser = data.get();
      existedUser.setUsername(deliverer.getUsername());
      existedUser.setEmail(deliverer.getEmail());
      existedUser.setPassword(deliverer.getPassword());
      existedUser.setAddress(deliverer.getAddress());
      existedUser.setPhone(deliverer.getPhone());
      existedUser.setCarPlate(deliverer.getCarPlate());
      existedUser.setRatingAmount(deliverer.getRatingAmount());
      existedUser.setRating(deliverer.getRating());
      return delivererRepository.save(existedUser);
    } else {
      return null;
    }

  }

  public User adminUpdateUser(User user) {
    Optional<User> data = repository.findById(user.getId());
    if (data.isPresent()) {
      User existedUser = data.get();
      existedUser.setUsername(user.getUsername());
      existedUser.setEmail(user.getEmail());
      existedUser.setPassword(user.getPassword());
      existedUser.setAddress(user.getAddress());
      existedUser.setPhone(user.getPhone());
      return repository.save(existedUser);
    } else {
      return null;
    }

  }

  public void deleteUserById(int userId) {
    repository.deleteById(userId);
  }

}
	
	
	
	
	
	
	
	
	


