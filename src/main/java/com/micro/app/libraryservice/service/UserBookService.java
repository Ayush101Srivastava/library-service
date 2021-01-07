package com.micro.app.libraryservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.micro.app.libraryservice.entity.UserBookAssociation;
import com.micro.app.libraryservice.model.User;
import com.micro.app.libraryservice.model.UserProfile;
import com.micro.app.libraryservice.repository.UserBookAssociationRepository;

@Service
public class UserBookService {
	private UserBookAssociationRepository userBookAssociationRepository;
	private UserService userService;
	private BookService bookService;

	public UserBookService(UserBookAssociationRepository userBookAssociationRepository, UserService userService,
			BookService bookService) {
		super();
		this.userBookAssociationRepository = userBookAssociationRepository;
		this.userService = userService;
		this.bookService = bookService;
	}

	public UserProfile fetchUserProfile(Long id) {
		UserProfile profile = new UserProfile();
		User fetchedUser = userService.fetchUserById(id);
		profile.setUser(fetchedUser);
		profile.setIssuedBooks(new ArrayList<>());
		List<Long> issuedBooks = userBookAssociationRepository.findAllBookIdByUserId(id);
		for (Long bookId : issuedBooks) {
			profile.getIssuedBooks().add(bookService.fetchBookById(bookId));
		}
		return profile;
	}

	public Long releaseIssuedBooks(Long id) {
		return userBookAssociationRepository.deleteByUserId(id);

	}

	public void issueBookToUser(Long bookId, Long userId) {
		UserBookAssociation issueBook = new UserBookAssociation();
		issueBook.setBookId(bookId);
		issueBook.setUserId(userId);
		userBookAssociationRepository.saveAndFlush(issueBook);

	}

	public void releaseIssuedBookFromUser(Long bookId, Long userId) {
		userBookAssociationRepository.deleteByBookIdAndUserId(bookId, userId);
	}

}
