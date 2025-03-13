package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Article;
import ru.hpclab.hl.module1.repository.ArticleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(String id) {
        return articleRepository.findById(UUID.fromString(id));
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public void deleteArticle(String id) {
        articleRepository.delete(UUID.fromString(id));
    }

    public Article updateArticle(String id, Article article) {
        article.setIdentifier(UUID.fromString(id));
        return articleRepository.put(article);
    }
}
