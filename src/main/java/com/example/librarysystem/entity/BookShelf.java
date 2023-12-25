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
public class BookShelf extends BaseEntity{
    private String number;
    @OneToMany(mappedBy = "bookShelf")
    private List<ShelfRow> rows;
    @ManyToOne(fetch = FetchType.EAGER)
    private Floor floor;
}
