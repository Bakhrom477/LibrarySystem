package com.example.librarysystem.controller;

import com.example.librarysystem.dto.*;
import com.example.librarysystem.entity.enums.Status;
import com.example.librarysystem.repository.BookRepository;
import com.example.librarysystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/save")
    public BookResponseDto save(@RequestBody BookRequestDto bookRequestDto){
        return bookService.save(bookRequestDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/delete")
    public void delete(@RequestParam UUID bookId){
        bookService.delete(bookId);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping
    public BookResponseDto update(@RequestParam UUID bookId,@RequestBody BookRequestDto bookRequestDto){
        return bookService.update(bookId,bookRequestDto);
    }

    @GetMapping("get-with-floor-and-shelf")
    public List<ShelfRowResponseDto> findByFloorAndShelf(@RequestParam String floor, @RequestParam String shelf){
        return bookService.getShelfRowsByFloorAndShelf(floor,shelf);
    }

    @PostMapping("/borrow")
    public ContractResponseDto borrow(@RequestBody ContractRequestDto contractRequestDto){
        return bookService.borrow(contractRequestDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/change-status")
    public BookResponseDto changeStatus(@RequestParam UUID bookId, @RequestParam Status status){
        return bookService.changeStatus(bookId,status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-contracts")
    public List<ContractResponseDto> getAllContracts(){
        return bookService.getAllContracts();
    }
}
