package com.example.librarysystem.repository;

import com.example.librarysystem.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
    Optional<Contract> findByBookId(UUID bookId);
}
