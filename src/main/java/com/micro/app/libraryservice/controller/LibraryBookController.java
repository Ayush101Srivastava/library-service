package com.micro.app.libraryservice.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.app.libraryservice.model.Book;
import com.micro.app.libraryservice.service.BookService;
import com.micro.app.libraryservice.service.LibraryBookService;

@RestController
@RequestMapping("/api/v1/library/books")
public class LibraryBookController {

	private BookService bookService;
	private LibraryBookService libraryBookService;

	public LibraryBookController(BookService bookService, LibraryBookService libraryBookService) {
		super();
		this.bookService = bookService;
		this.libraryBookService = libraryBookService;
	}

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<Book> listAllBook() {
		return bookService.fetchAllBooks();
	}

	@GetMapping(path = "/{book_id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Book getBookById(@PathVariable(name = "book_id") Long id) {
		return bookService.fetchBookById(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.NESTED)
	public void addBook(@RequestBody Book book) {
		Book persistedBook = bookService.addBook(book);
		libraryBookService.addBook(persistedBook);
	}

	@DeleteMapping(path = "/book_id")
	@ResponseStatus(code = HttpStatus.OK)
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.NESTED)
	public void deleteBookById(@PathVariable(name = "book_id") Long id) {
		bookService.deleteById(id);
		libraryBookService.deleteById(id);
	}

}
