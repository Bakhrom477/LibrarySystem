package com.example.librarysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractRequestDto {
    private UUID bookId;
    private LocalDateTime borrowingTime=LocalDateTime.now();
}
