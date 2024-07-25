package com.akshat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.akshat.exception.ProductException;
import com.akshat.model.Product;
import com.akshat.model.Review;
import com.akshat.model.User;
import com.akshat.repsitory.ProductRepository;
import com.akshat.repsitory.ReviewRepository;
import com.akshat.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService{

	private ReviewRepository reviewRepository;
	private ProductService productService;
	
	private ProductRepository productRepository;
	
	public ReviewServiceImplementation(ReviewRepository reviewRepository,ProductService productService,ProductRepository productRepository) {
		
		this.reviewRepository=reviewRepository;
		this.productService=productService;
		
	}
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product =productService.findProductById(req.getProductId());
		
		Review review =new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
