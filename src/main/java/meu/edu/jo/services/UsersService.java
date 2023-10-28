package meu.edu.jo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Users;
import meu.edu.jo.repositories.UsersRepository;

import java.util.List;

@Service
public class UsersService {

	@Autowired
	private UsersRepository userRepository;

	public List<Users> getAllUsers() {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
		}
	}

	public Users getUserById(Long id) {
		try {
			return userRepository.findById(id).orElseThrow(() -> new CustomException(SystemMessages.NO_RECORDS + id));
		} catch (Exception e) {
			throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
		}
	}

	public Users createUser(Users user) {
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException ex) {
			throw new CustomException(
					"Username " + user.getUsername() + " is already taken. Please choose a different username.");

		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}

	public Users updateUser(Long id, Users user) {
		try {
			Users existingUser = userRepository.findById(id)
					.orElseThrow(() -> new CustomException(SystemMessages.NO_RECORDS + id));
			existingUser.setUsername(user.getUsername());
			existingUser.setPassword(user.getPassword());
			return userRepository.save(existingUser);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}

	public void deleteUser(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}
}
