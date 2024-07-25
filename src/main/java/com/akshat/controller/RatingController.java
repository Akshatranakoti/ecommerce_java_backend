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
import com.akshat.exception.ProductException;
import com.akshat.exception.UserException;
import com.akshat.model.Address;
import com.akshat.model.Order;
import com.akshat.model.Rating;
import com.akshat.model.User;
import com.akshat.service.OrderService;
import com.akshat.service.RatingRequest;
import com.akshat.service.RatingService;
import com.akshat.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Rating>createRating(@RequestBody RatingRequest req,@RequestHeader("Authorizatioin")String jwt)throws UserException,ProductException{
		
		User user =userService.findUserProfileByJwt(jwt);	
		Rating rating=ratingService.createRating(req,user);
		
		
		return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
		
		
	}

	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>>getProductsRating(@PathVariable("id") Long productId,@RequestHeader("Authorizatioin")String jwt)throws UserException,ProductException{
		
		User user =userService.findUserProfileByJwt(jwt);	
		List<Rating>ratings =ratingService.getProductsRating(productId);
		return new ResponseEntity<>(ratings,HttpStatus.CREATED);
		
		
	}
	
}
