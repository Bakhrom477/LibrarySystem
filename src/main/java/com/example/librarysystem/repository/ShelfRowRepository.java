package com.example.librarysystem.repository;

import com.example.librarysystem.entity.ShelfRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShelfRowRepository extends JpaRepository<ShelfRow, UUID> {
    List<ShelfRow> findByFloorNumberAndBookShelfNumber(String floorNumber, String shelfNumber);
}
