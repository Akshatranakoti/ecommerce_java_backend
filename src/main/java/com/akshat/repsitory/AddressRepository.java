package com.akshat.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshat.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{
	

}
