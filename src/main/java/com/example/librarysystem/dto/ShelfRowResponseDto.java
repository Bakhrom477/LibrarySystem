package com.example.librarysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShelfRowResponseDto {
    private UUID id;
    private int numberOfBooks;
    private List<BookResponseDto> books;
}
