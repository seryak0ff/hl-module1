package ru.hpclab.hl.module1.model;

import org.springframework.lang.NonNull;

import java.util.UUID;
import java.time.LocalDate;

public class User {
    @NonNull
    private UUID identifier;
    @NonNull
    private String login;
    @NonNull
    private String university;
    @NonNull
    private LocalDate subscriptionEndDate;

    // Конструкторы
    public User(@NonNull UUID identifier, @NonNull String login, @NonNull String university,
                @NonNull LocalDate subscriptionEndDate) {
        this.identifier = identifier;
        this.login = login;
        this.university = university;
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public User() {
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
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    @NonNull
    public String getUniversity() {
        return university;
    }

    public void setUniversity(@NonNull String university) {
        this.university = university;
    }

    @NonNull
    public LocalDate getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setUniversity(@NonNull LocalDate subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }


    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", login='" + login + '\'' +
                ", university='" + university + '\'' +
                ", subscriptionEndDate=" + subscriptionEndDate +
                '}';
    }
}
