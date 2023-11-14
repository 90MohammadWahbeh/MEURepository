package meu.edu.jo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.services.UsersService;
import meu.edu.jo.entities.Users;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		try {
			List<Users> users = usersService.getAllUsers();
			return ResponseEntity.ok(users);
		} catch (CustomException e) {
			throw e;
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUsersById(@PathVariable Long id) {
		try {
			Users user = usersService.getUserById(id);
			return ResponseEntity.ok(user);
		} catch (CustomException e) {
			throw e;
		}
	}

	@PostMapping
	public ResponseEntity<?> createUsers(@RequestBody Users user) {
		try {
			Users createdUser = usersService.createUser(user);
			return ResponseEntity.ok(createdUser);
		} catch (CustomException e) {
			throw e;
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUsers(@PathVariable Long id, @RequestBody Users user) {
		try {
			Users updatedUser = usersService.updateUser(id, user);
			return ResponseEntity.ok(updatedUser);
		} catch (CustomException e) {
			throw e;
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUsers(@PathVariable Long id) {
		try {
			usersService.deleteUser(id);
			return new ResponseEntity<String>(SystemMessages.DELETE_MSG, HttpStatus.OK);
		} catch (CustomException e) {
			throw e;
		}
	}
}
