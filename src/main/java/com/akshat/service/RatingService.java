package com.akshat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akshat.exception.ProductException;
import com.akshat.model.Rating;
import com.akshat.model.User;

@Service
public interface RatingService {
	
	public Rating createRating(RatingRequest req, User user)throws ProductException;
		
	public List<Rating>getProductsRating(Long productId);
	

}
