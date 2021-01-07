package com.micro.app.libraryservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LIBRARY_BOOK")
@Data
@NoArgsConstructor
public class LibraryBookAssociation {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "book_id")
	private Long bookId;
}
