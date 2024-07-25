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

import com.akshat.exception.ProductException;
import com.akshat.exception.UserException;
import com.akshat.model.Rating;
import com.akshat.model.Review;
import com.akshat.model.User;
import com.akshat.request.ReviewRequest;
import com.akshat.service.RatingRequest;
import com.akshat.service.RatingService;
import com.akshat.service.ReviewService;
import com.akshat.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Review>createReviewReview(@RequestBody ReviewRequest  req ,@RequestHeader("Authorizatioin")String jwt)throws UserException,ProductException{
		
		User user =userService.findUserProfileByJwt(jwt);	
		Review review=reviewService.createReview(req,user);
		
		
		return new ResponseEntity<>(review,HttpStatus.CREATED);
		
		
	}
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>>getProductsReview(@PathVariable Long productId)throws UserException,ProductException{
		
		List<Review>reviews =reviewService.getAllReview(productId);
		return new ResponseEntity<>(reviews,HttpStatus.ACCEPTED);
		
		
	}

}
