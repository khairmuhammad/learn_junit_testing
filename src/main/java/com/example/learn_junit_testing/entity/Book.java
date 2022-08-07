package com.example.learn_junit_testing.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_record")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String name;

    private String summary;

    private int rating;
}
