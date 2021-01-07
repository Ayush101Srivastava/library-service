package com.micro.app.libraryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.app.libraryservice.service.UserBookService;

@RestController
@RequestMapping(path = "/api/v1/library/users/{user_id}/books/{book_id}")
public class BookIssueAndReleaseController {

	private UserBookService userBookService;

	public BookIssueAndReleaseController(UserBookService userBookService) {
		super();
		this.userBookService = userBookService;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void issueBookToUser(@PathVariable(name = "user_id") Long userId,
			@PathVariable(name = "book_id") Long bookId) {
		userBookService.issueBookToUser(bookId, userId);
	}
	
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.OK)
	public void releaseBookFromUser(@PathVariable(name = "user_id") Long userId, @PathVariable(name = "book_id") Long bookId) {
		userBookService.releaseIssuedBookFromUser(bookId,userId);
	}

}
