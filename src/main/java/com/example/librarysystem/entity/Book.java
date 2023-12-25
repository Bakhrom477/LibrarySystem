package com.example.librarysystem.entity;

import com.example.librarysystem.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book extends BaseEntity{
    @Column(unique = true)
    private String title;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String author;
    @ManyToOne(fetch = FetchType.EAGER)
    private ShelfRow shelfRow;
}
