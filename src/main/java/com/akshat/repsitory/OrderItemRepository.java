package com.akshat.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshat.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

}
