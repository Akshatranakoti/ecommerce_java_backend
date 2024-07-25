package com.akshat.service;

import com.akshat.exception.CartItemException;
import com.akshat.exception.UserException;
import com.akshat.model.Cart;
import com.akshat.model.CartItem;
import com.akshat.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId,Long id, CartItem cartItem) throws CartItemException,UserException;
	
	
	public CartItem isCartItemExist(Cart cart,Product product, String size, Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException ,UserException;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;
	
}
