package com.akshat.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.akshat.exception.OrderException;
import com.akshat.model.Address;
import com.akshat.model.Cart;
import com.akshat.model.CartItem;
import com.akshat.model.Order;
import com.akshat.model.OrderItem;
import com.akshat.model.User;
import com.akshat.repsitory.AddressRepository;
import com.akshat.repsitory.CartRepository;
import com.akshat.repsitory.OrderItemRepository;
import com.akshat.repsitory.OrderRepository;
import com.akshat.repsitory.UserRepository;

@Service
public class OrderServiceImplementation implements OrderService {
	private OrderRepository orderRepository;

	private AddressRepository addressRepository;
	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;
	private CartService cartService;
	private ProductService productService;
	private UserRepository userRepository;
	
	
	public OrderServiceImplementation( OrderRepository orderRepository
			,CartService cartService,UserRepository userRepository
			,AddressRepository addressRepository, OrderItemRepository orderItemRepository,OrderItemService orderItemService) {
		this.orderRepository=orderRepository;
		
		this.cartService=cartService;
		this.userRepository=userRepository;
		this.addressRepository=addressRepository;
		this.orderItemRepository=orderItemRepository;
		this.orderItemService=orderItemService;
		
	}

	@Override
	public Order createOrder(User user, Address shippAddress) {
		
		shippAddress.setUser(user);
		Address address=addressRepository.save(shippAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		Cart cart=cartService.findUserCart(user.getId());
		List<OrderItem>orderItems=new ArrayList<>();
		for(CartItem item:cart.getCartItems()) {
			OrderItem orderItem=new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			orderItems.add(createdOrderItem);
		}
		
		Order createdOrder=new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscounte(cart.getDiscounte());
		createdOrder.setTotalItem(cart.getTotalItem());
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("Pending");
		createdOrder.getPaymentDetails().setStatus("pending");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		
		Order savedOrder=orderRepository.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		return savedOrder;
	
		

	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
	Optional<Order>opt=orderRepository.findById(orderId);
	if(opt.isPresent()) {
		return opt.get();
	}
		throw new OrderException("order not exist with id"+orderId);
		
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order>orders=orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("Placed");
		order.getPaymentDetails().setStatus("Completed");;
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("Confirmed");
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
	Order order=findOrderById(orderId);
	order.setOrderStatus("Shipped");
	return orderRepository.save(order);
		
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("Delivered");
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("Cancelled");
		return orderRepository.save(order);
		
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		 orderRepository.deleteById(orderId);
		 
	}

}
