package com.akshat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.exception.OrderException;
import com.akshat.exception.UserException;
import com.akshat.model.Address;
import com.akshat.model.Cart;
import com.akshat.model.Order;
import com.akshat.model.User;
import com.akshat.service.CartService;
import com.akshat.service.OrderService;
import com.akshat.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/")
	public ResponseEntity<Order>createOrder(@RequestBody Address shippingAddress,@RequestHeader("Authorization")String jwt)throws UserException{
		
		User user =userService.findUserProfileByJwt(jwt);	
		Order order=orderService.createOrder(user,shippingAddress);
		System.out.println("ORDER"+order);
		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>>usersOrderHistory(@RequestHeader("Authorization")String jwt)throws UserException{
		
		User user =userService.findUserProfileByJwt(jwt);	
		List<Order> order=orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
		
		
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<Order>findUserCart(@PathVariable("Id") Long orderId,@RequestHeader("Authorization")String jwt)throws UserException,OrderException{
		
		User user =userService.findUserProfileByJwt(jwt);	
		Order order=orderService.findOrderById(orderId);
		return new ResponseEntity<>(order,HttpStatus.CREATED);
		
		
	}
	
}
