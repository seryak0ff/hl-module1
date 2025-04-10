package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Article;
import ru.hpclab.hl.module1.repository.ArticleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Article addArticle(Article article) {
        return repository.save(article);
    }

    public Article getArticle(String id) {
        return repository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<Article> getAllArticles() {
        return repository.findAll();
    }

    public void deleteArticle(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    public void clearAllArticles(){ repository.deleteAll(); }
}
