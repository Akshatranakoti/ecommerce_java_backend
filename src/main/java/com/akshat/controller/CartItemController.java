package com.akshat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akshat.exception.CartItemException;
import com.akshat.exception.UserException;
import com.akshat.model.Cart;
import com.akshat.model.CartItem;
import com.akshat.model.Product;
import com.akshat.model.User;
import com.akshat.request.AddItemRequest;
import com.akshat.response.ApiResponse;
import com.akshat.service.CartItemService;
import com.akshat.service.CartService;
import com.akshat.service.UserService;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;
    
    @Autowired
    private UserService userService;
    
//    @Autowired
//    private CartService cartService;
    
//    @PostMapping("/{cartItemId}")
//    public ResponseEntity<ApiResponse> addCartItem(@RequestBody AddItemRequest request, @RequestHeader("Authorization") String jwt) 
//            throws UserException, CartItemException {
//        User user = userService.findUserProfileByJwt(jwt);
//        
//        // Create a new CartItem object from the request
//        CartItem cartItem = new CartItem();
//        cartItem.setProduct(request.getProduct());
//        cartItem.setSize(request.getSize());
//        cartItem.setQuantity(request.getQuantity());
//        
//        // Assuming Cart is related to the user and you need to fetch it
//        Cart cart = cartService.findUserCart(user.getId());
//        cartItem.setCart(cart);
//
//        // Save the cart item
//        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
//        
//        ApiResponse response = new ApiResponse();
//        response.setMessage("Item added to cart");
//        response.setStatus(true);
//        
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem ,@PathVariable Long cartItemId, 
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        
       CartItem updatedCartItem=cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
       return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) 
            throws UserException, CartItemException {
    	System.out.println(cartItemId);
        User user = userService.findUserProfileByJwt(jwt);
        
        cartItemService.removeCartItem(user.getId(), cartItemId);
        
        ApiResponse response = new ApiResponse();
        response.setMessage("Cart item removed");
        response.setStatus(true);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // Optionally: Add more methods as needed for other operations
}
