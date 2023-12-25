package com.example.librarysystem.dto;

import com.example.librarysystem.entity.BookShelf;
import com.example.librarysystem.entity.ShelfRow;
import com.example.librarysystem.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseDto {
    private String title;
    private String author;
    private Status status;
    private String floor;
    private String bookShelf;
    private String shelfRow;
}
