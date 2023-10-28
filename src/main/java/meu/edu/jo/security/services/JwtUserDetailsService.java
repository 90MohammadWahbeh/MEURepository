package meu.edu.jo.security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import meu.edu.jo.entities.Users;
import meu.edu.jo.repositories.UsersRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Users user = userRepository.findByusername(username);

	    if (user == null) {
	        throw new UsernameNotFoundException("User not found with username: " + username);
	    }

	    return new User(user.getUsername(), BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()), user.getAuthorities());
	}


}