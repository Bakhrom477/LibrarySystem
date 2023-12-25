package com.example.librarysystem.repository;

import com.example.librarysystem.entity.BookShelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, UUID> {
}
