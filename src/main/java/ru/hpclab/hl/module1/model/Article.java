package ru.hpclab.hl.module1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_article")
public class Article {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private String doi;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private int publicationYear;

}
