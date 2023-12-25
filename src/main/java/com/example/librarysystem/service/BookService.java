package com.example.librarysystem.service;

import com.example.librarysystem.dto.*;
import com.example.librarysystem.entity.*;
import com.example.librarysystem.entity.enums.Status;
import com.example.librarysystem.exception.DataAlreadyExistsException;
import com.example.librarysystem.exception.DataNotFoundException;
import com.example.librarysystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final ShelfRowRepository shelfRowRepository;
    private final BookShelfRepository bookShelfRepository;
    private final FloorRepository floorRepository;
    private final ContractRepository contractRepository;

    public BookResponseDto save(BookRequestDto bookRequestDto){
        Book book = mapReqToEntity(bookRequestDto);
        Book saved = bookRepository.save(book);
        return mapEntityToRES(saved);
    }

    public BookResponseDto update(UUID bookId,BookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book not found while updating."));
        ShelfRow shelfRow = shelfRowRepository.findById(bookRequestDto.getShelfRow())
                .orElseThrow(() -> new DataNotFoundException("Shelf Row Not Found While Adding Book."));
        book.setTitle(bookRequestDto.getTitle());
        book.setStatus(bookRequestDto.getStatus());
        book.setShelfRow(shelfRow);
        Book saved = bookRepository.save(book);
        return mapEntityToRES(saved);
    }

    public BookResponseDto findByTitle(String title){
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new DataNotFoundException("Book not found while updating."));

        return mapEntityToRES(book);
    }
    public ContractResponseDto borrow(ContractRequestDto contractRequestDto){
        Book book = bookRepository.findById(contractRequestDto.getBookId())
                .orElseThrow(() -> new DataNotFoundException("Book not found while updating."));
        if (book.getStatus().equals(Status.OPEN)){
            Contract contract = new Contract(book,contractRequestDto.getBorrowingTime().plusDays(3));
            contractRepository.save(contract);
            book.setStatus(Status.BORROWED);
            BookResponseDto bookResponseDto = mapEntityToRES(book);
            return modelMapper.map(new ContractResponseDto(bookResponseDto,contract.getReturningTime()),ContractResponseDto.class);
        }else {
            throw new DataAlreadyExistsException("Contract already exists with this book.");
        }
    }
    public BookResponseDto findById(UUID bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book not found while updating."));

        return mapEntityToRES(book);
    }
    public List<BookResponseDto> getAll(){
        List<BookResponseDto> allBooks=new ArrayList<>();
        List<Book> books = bookRepository.findAll();

        return books.stream().map(this::mapEntityToRES).collect(Collectors.toList());

    }
    public List<ShelfRowResponseDto> getShelfRowsByFloorAndShelf(String floor,String shelf){
        List<ShelfRowResponseDto> shelfRowResponseDtos=new ArrayList<>();
        List<ShelfRow> shelfRows = shelfRowRepository.findByFloorNumberAndBookShelfNumber(floor, shelf);
        for (ShelfRow shelfRow : shelfRows) {
            List<BookResponseDto> bookResponseDtos=new ArrayList<>();
            for (Book book : shelfRow.getBooks()) {
                bookResponseDtos.add(mapEntityToRES(book));
            }
            shelfRowResponseDtos.add(new ShelfRowResponseDto(
                    shelfRow.getId(),
                    shelfRow.getBooks().size(),
                    bookResponseDtos
                    ));
        }
        return shelfRowResponseDtos;
    }
    public List<ContractResponseDto> getAllContracts(){
        List<Contract> all = contractRepository.findAll();
        List<ContractResponseDto> allContracts=new ArrayList<>();
        for (Contract contract : all) {
            BookResponseDto bookResponseDto = mapEntityToRES(contract.getBook());
            allContracts.add(modelMapper.map(new ContractResponseDto(bookResponseDto,contract.getReturningTime()),ContractResponseDto.class));
        }
        return allContracts;
    }
    public void delete(UUID bookId){
        bookRepository.deleteById(bookId);
    }
    protected Book mapReqToEntity(BookRequestDto bookRequestDto) {
        Book book = modelMapper.map(bookRequestDto, Book.class);
        ShelfRow shelfRow = shelfRowRepository.findById(bookRequestDto.getShelfRow())
                .orElseThrow(() -> new DataNotFoundException("Shelf Row Not Found While Adding Book."));
        if (shelfRow.getBooks().size()==10){
            throw new DataAlreadyExistsException("Books are enough here");
        }
        book.setShelfRow(shelfRow);
        return book;
    }
    protected BookResponseDto mapEntityToRES(Book entity) {
        BookShelf bookShelf = bookShelfRepository.findById(entity.getShelfRow().getBookShelf().getId())
                .orElseThrow(() -> new DataNotFoundException("BookShelf not found"));
        Floor floor = floorRepository.findById(bookShelf.getFloor().getId())
                .orElseThrow(() -> new DataNotFoundException("Floor not found"));
        return new BookResponseDto(
             entity.getTitle(),
                entity.getAuthor(),
             entity.getStatus(),
             floor.getNumber(),
             bookShelf.getNumber(),
             entity.getShelfRow().getNumber());

    }
    public BookResponseDto changeStatus(UUID bookId,Status status) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataNotFoundException("Book not found while updating."));
        book.setStatus(status);
        bookRepository.save(book);
        return mapEntityToRES(book);
    }
}
