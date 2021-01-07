package com.micro.app.libraryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.app.libraryservice.entity.UserBookAssociation;

public interface UserBookAssociationRepository extends JpaRepository<UserBookAssociation, Long> {

	List<Long> findAllBookIdByUserId(Long userId);

	Long deleteByUserId(Long id);

	void deleteByBookIdAndUserId(Long bookId, Long userId);

}
