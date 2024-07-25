package com.akshat.service;

import java.util.List;

import com.akshat.exception.ProductException;
import com.akshat.model.Review;
import com.akshat.model.User;
import com.akshat.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user)throws ProductException;
	
	public List<Review>getAllReview(Long productId);
	
}
