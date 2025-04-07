package ru.hpclab.hl.module1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_download")
public class Download {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "download_date", nullable = false)
    private LocalDateTime downloadDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DownloadFormat format;

    public enum DownloadFormat {
        PDF, HTML
    }
}
