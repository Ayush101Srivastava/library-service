package com.micro.app.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.app.libraryservice.entity.LibraryBookAssociation;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryBookAssociation, Long>{

}
