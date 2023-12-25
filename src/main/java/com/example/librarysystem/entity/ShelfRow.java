package com.example.librarysystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShelfRow extends BaseEntity{
    private String number;
    @ManyToOne(fetch = FetchType.EAGER)
    private BookShelf bookShelf;
    @ManyToOne(fetch = FetchType.EAGER)
    private Floor floor;
    @OneToMany(mappedBy = "shelfRow")
    private List<Book> books;

}
