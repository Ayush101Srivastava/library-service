package com.micro.app.libraryservice.service;

import org.springframework.stereotype.Service;

import com.micro.app.libraryservice.entity.LibraryBookAssociation;
import com.micro.app.libraryservice.model.Book;
import com.micro.app.libraryservice.repository.LibraryBookAssociationRepository;

@Service
public class LibraryBookService {

	private LibraryBookAssociationRepository libraryBookAssociationRepository;

	public LibraryBookService(LibraryBookAssociationRepository libraryBookAssociationRepository) {
		super();
		this.libraryBookAssociationRepository = libraryBookAssociationRepository;
	}

	public void addBook(Book book) {
		LibraryBookAssociation association = new LibraryBookAssociation();
		association.setBookId(book.getId());
		libraryBookAssociationRepository.saveAndFlush(association);
	}

	public void deleteById(Long id) {
		libraryBookAssociationRepository.deleteByBookId(id);
	}

}
