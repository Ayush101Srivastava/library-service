package com.micro.app.libraryservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.app.libraryservice.model.Book;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class BookService {

	@Value("http://${book.service.name}/api/${book.service.version}/books")
	private String bookServiceUrl;

	@Autowired
	private RestTemplate restTemplate;

	public List<Book> fetchAllBooks() {
		ResponseEntity<Book[]> response = restTemplate.getForEntity(bookServiceUrl, Book[].class);
		return Arrays.asList(response.getBody());
	}

	@HystrixCommand(fallbackMethod = "getFallBackBookById", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		})
	public Book fetchBookById(Long id) {
		ResponseEntity<Book> response = restTemplate.getForEntity(bookServiceUrl + "/" + id, Book.class);
		return response.getBody();
	}

	public Book addBook(Book book) {
		return restTemplate.postForEntity(bookServiceUrl, book, Book.class).getBody();
	}

	public void deleteById(Long id) {
		restTemplate.delete(bookServiceUrl, id);

	}

	public Book getFallBackBookById(Long id) {
		return new Book(id, "Not Available", "Not Available", "Not Available");
	}

}
