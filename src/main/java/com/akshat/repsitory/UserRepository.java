package com.akshat.repsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import com.akshat.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findByEmail(String email);
	

}
