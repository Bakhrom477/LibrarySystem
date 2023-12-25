package com.example.librarysystem.service;

import com.example.librarysystem.entity.BookShelf;
import com.example.librarysystem.entity.Floor;
import com.example.librarysystem.entity.ShelfRow;
import com.example.librarysystem.repository.BookShelfRepository;
import com.example.librarysystem.repository.FloorRepository;
import com.example.librarysystem.repository.ShelfRowRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultService {

    private final FloorRepository floorRepository;
    private final BookShelfRepository bookShelfRepository;
    private final ShelfRowRepository shelfRowRepository;

//    @PostConstruct
//    public void addFloors() {
//        for (int i = 0; i < 4; i++) {
//            Floor floor = new Floor();
//            floor.setNumber(String.valueOf(i + 1));
//            floorRepository.save(floor);
//        }
//    }
//    @PostConstruct
//    public void addBookShelves() {
//        List<BookShelf> all = bookShelfRepository.findAll();
//        for (BookShelf bookShelf : all) {
//            for (int j = 0; j <10 ; j++) {
//                ShelfRow shelfRow = new ShelfRow();
//                shelfRow.setNumber(String.valueOf(j+1));
//                shelfRow.setBookShelf(bookShelf);
//                shelfRow.setFloor(bookShelf.getFloor());
//               shelfRowRepository.save(shelfRow);
//            }
//        }
//    }
}
