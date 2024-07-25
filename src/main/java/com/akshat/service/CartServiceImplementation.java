package com.akshat.service;

import org.springframework.stereotype.Service;

import com.akshat.exception.ProductException;
import com.akshat.model.Cart;
import com.akshat.model.CartItem;
import com.akshat.model.Product;
import com.akshat.model.User;
import com.akshat.repsitory.CartRepository;
import com.akshat.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService{

	
	private CartRepository cartRepository;
	
	private CartItemService cartItemService;
	
	private ProductService productService;
	
	
	public CartServiceImplementation(CartRepository cartRepository,CartItemService cartItemService,ProductService productService) {
	
		this.cartRepository=cartRepository;
		this.cartItemService=cartItemService;
		this.productService=productService;
	}
	
	@Override
	public Cart createCart(User user) {
		Cart cart=new Cart();
		cart.setUser(user);
		
		
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart=cartRepository.findByUserId(userId);
		
	    Product product=productService.findProductById(req.getProductId());
	    
	    CartItem isPresent=cartItemService.isCartItemExist(cart, product, null, userId);
	    
	    if(isPresent==null) {
	    	CartItem cartItem=new CartItem();
	    	cartItem.setProduct(product);
	    	cartItem.setCart(cart);
	    	cartItem.setQuantity(req.getQuantity());
	    	cartItem.setUserId(userId);
	    	
	    	int price=req.getQuantity()*product.getDiscountedPrice();
	    	cartItem.setPrice(price);
	    	cartItem.setSize(req.getSize());
	    	
	    	CartItem createdCartItem=cartItemService.createCartItem(cartItem);
	    	cart.getCartItems().add(createdCartItem);
	    			}
		return  "Item Add To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		for(CartItem cartItem :cart.getCartItems()) {
			totalPrice=totalPrice+cartItem.getPrice();
			totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem+=totalItem+cartItem.getQuantity();
			
			
			
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		
		return cartRepository.save(cart);
	}

	
}
