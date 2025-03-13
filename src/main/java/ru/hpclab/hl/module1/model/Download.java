package ru.hpclab.hl.module1.model;

import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.UUID;

public class Download {
    @NonNull
    private UUID Id;
    @NonNull
    private User user;
    @NonNull
    private Article article;
    @NonNull
    private LocalDate downloadDate;
    @NonNull
    private String format;  // PDF или HTML

    // Конструкторы
    public Download(@NonNull User user, @NonNull Article article, @NonNull LocalDate downloadDate, @NonNull String format){
        this.user = user;
        this.article = article;
        this.downloadDate = downloadDate;
        this.format = format;
    }

    public Download(){
    }

    // Геттеры и сеттеры
    @NonNull
    public UUID getId() {
        return Id;
    }

    public void setId(@NonNull UUID Id) {
        this.Id = Id;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    @NonNull
    public Article getArticle() {
        return article;
    }

    public void setArticle(@NonNull Article article) {
        this.article = article;
    }

    @NonNull
    public LocalDate getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(@NonNull LocalDate downloadDate) {
            this.downloadDate = downloadDate;
    }

    @NonNull
    public String getFormat() {
        return format;
    }

    public void setFormat(@NonNull User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Download{" +
                "user=" + user +
                ", article=" + article +
                ", downloadDate=" + downloadDate +
                ", format='" + format + '\'' +
                '}';
    }
}
