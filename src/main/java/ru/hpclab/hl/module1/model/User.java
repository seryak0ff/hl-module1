package ru.hpclab.hl.module1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class User {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private LocalDate subscription_end_date;

}
