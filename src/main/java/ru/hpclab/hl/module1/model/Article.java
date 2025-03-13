package ru.hpclab.hl.module1.model;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class Article {
    @NonNull
    private UUID identifier;
    @NonNull
    private String doi;     // DOI (Digital Object Identifier)
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private int yearPublished;


    // Конструкторы
    public Article(@NonNull UUID identifier, @NonNull String doi, @NonNull String title,
                   @NonNull String author, @NonNull int yearPublished){
        this.identifier = identifier;
        this.doi = doi;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
    }

    public Article(){
    }

    // Геттеры и сеттеры
    @NonNull
    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(@NonNull UUID identifier) {
        this.identifier = identifier;
    }

    @NonNull
    public String getDoi(){return doi;}

    public void setDoi(@NonNull String doi){this.doi = doi;}

    @NonNull
    public String getTitle(){return title;}

    public void setTitle(@NonNull String title){this.title = title;}

    @NonNull
    public String getAuthor(){return author;}

    public void setAuthor(@NonNull String author){this.author = author;}

    @NonNull
    public int getYearPublished(){return yearPublished;}

    public void setYearPublished(@NonNull int yearPublished){this.yearPublished = yearPublished;}


    @Override
    public String toString() {
        return "Article{" +
                "identifier=" + identifier +
                ", doi='" + doi + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearPublished=" + yearPublished +
                '}';
    }
}

