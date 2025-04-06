package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hpclab.hl.module1.model.Article;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
