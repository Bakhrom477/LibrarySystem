package com.example.librarysystem.repository;

import com.example.librarysystem.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FloorRepository extends JpaRepository<Floor, UUID> {
}
