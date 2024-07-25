package com.akshat.service;

import com.akshat.exception.ProductException;
import com.akshat.model.Cart;
import com.akshat.model.User;
import com.akshat.request.AddItemRequest;

public interface CartService{

	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);

}
