package com.akshat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.service.CartService;
import com.akshat.service.CustomUserServiceImplementation;

import com.akshat.config.JwtProvider;
import com.akshat.exception.UserException;
import com.akshat.model.Cart;
import com.akshat.model.User;
import com.akshat.repsitory.UserRepository;
import com.akshat.request.LoginRequest;
import com.akshat.response.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomUserServiceImplementation customUserService;
	private CartService cartService;
	
	public AuthController( UserRepository userRepository,CustomUserServiceImplementation customUserService
			,PasswordEncoder passwordEncoder,JwtProvider jwtProvider,CartService cartService) {
		this.userRepository=userRepository;
		this.customUserService=customUserService;
		this.passwordEncoder=passwordEncoder;
		this.jwtProvider=jwtProvider;
		this.cartService=cartService;
		System.out.print(this.passwordEncoder);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user)throws UserException{

		
		String email=user.getEmail();
		String password=user.getPassword();
		String firstName=user.getFirstName();
		String lastName=user.getLastName();
		
		User isEmailExist=userRepository.findByEmail(email);
		
		if(isEmailExist!=null) {
			throw new UserException("Email is Already User with Another Account");
		}
		
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		User savedUser=userRepository.save(createdUser);
		Cart cart=cartService.createCart(savedUser);
		
		Authentication authentication =new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("SignUp Sucess");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
		
		String username=loginRequest.getEmail();
		String password =loginRequest.getPassword();
		
		
		Authentication authentication= authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
        
		String token=jwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("SignIn Sucess");
	
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
		
		
		}

	private Authentication authenticate(String username, String password) {
		System.out.println("vlock");
		UserDetails userDetails=customUserService.loadUserByUsername(username);
		System.out.println(userDetails);
		System.out.println("yes");
		
		
		if(userDetails==null) {
			System.out.println("invalid username");
			throw new BadCredentialsException("Invalid Username...");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			System.out.println("invalid Password");
			throw new BadCredentialsException("Invalid Password...");
		}
		System.out.println("vlock2");
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}

}

   
