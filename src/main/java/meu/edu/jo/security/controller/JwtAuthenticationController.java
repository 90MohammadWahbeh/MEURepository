package meu.edu.jo.security.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import meu.edu.jo.entities.Users;
import meu.edu.jo.repositories.UsersRepository;
import meu.edu.jo.security.model.JwtRequest;
import meu.edu.jo.security.utils.JwtTokenUtil;


@CrossOrigin(origins = "*")
@RestController
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	
	@Autowired
	private UsersRepository userRepository;


	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
	    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
	    final UserDetails userDetails = jwtInMemoryUserDetailsService
	            .loadUserByUsername(authenticationRequest.getUsername());
	    final String token = jwtTokenUtil.generateToken(userDetails, getUserIDByUsername(authenticationRequest.getUsername()));
	    Map<String, Object> response = new HashMap<>();
	    response.put("token", token);
	    response.put("userId", getUserIDByUsername(authenticationRequest.getUsername()));

	    return ResponseEntity.ok(response);

	}

	

	private Long getUserIDByUsername(String username) {
		Users user =  userRepository.findByusername(username);
	    return user.getId();
	}
	



	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
