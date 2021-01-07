package com.micro.app.libraryservice.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.app.libraryservice.model.User;
import com.micro.app.libraryservice.model.UserProfile;
import com.micro.app.libraryservice.service.UserBookService;
import com.micro.app.libraryservice.service.UserService;

@RestController
@RequestMapping("/api/v1/library/users")
public class LibraryUserController {

	private UserService userService;
	private UserBookService userBookService;

	public LibraryUserController(UserService userService, UserBookService userBookService) {
		super();
		this.userService = userService;
		this.userBookService = userBookService;
	}

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<User> listAllUsers() {
		return userService.fetchAllUsers();
	}

	@GetMapping("/{user_id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UserProfile fetchUserProfile(@PathVariable(name = "user_id") Long id) {
		return userBookService.fetchUserProfile(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@DeleteMapping(name = "/{user_id}")
	@ResponseStatus(code = HttpStatus.OK)
	@Transactional
	public void deleteUser(@PathVariable(name = "user_id") Long id) {
		userBookService.releaseIssuedBooks(id);
		userService.deleteUser(id);
	}

	@PutMapping(path = "/{user_id}")
	public void updateUser(@RequestBody User user, @PathVariable(name = "user_id") Long id) {
		userService.updateUser(user, id);
	}

}