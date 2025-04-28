package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Article;
import ru.hpclab.hl.module1.repository.ArticleRepository;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {
    private final ArticleRepository repository;
    private final ObservabilityService observabilityService;


    public ArticleService(ObservabilityService observabilityService, ArticleRepository repository) {
        this.observabilityService = observabilityService;
        this.repository = repository;
    }

    public Article addArticle(Article article) {
        this.observabilityService.start(getClass().getSimpleName() + ":addArticle");
        Article temp = repository.save(article);
        this.observabilityService.stop(getClass().getSimpleName() + ":addArticle");
        return temp;
    }

    public Article getArticle(String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":getArticle");
        Article temp = repository.findById(UUID.fromString(id)).orElse(null);
        this.observabilityService.stop(getClass().getSimpleName() + ":getArticle");
        return temp;
    }

    public List<Article> getAllArticles() {
        this.observabilityService.start(getClass().getSimpleName() + ":getAllArticles");
        List<Article> temp = repository.findAll();
        this.observabilityService.stop(getClass().getSimpleName() + ":getAllArticles");
        return temp;

    }

    public void deleteArticle(String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":deleteArticle");
        repository.deleteById(UUID.fromString(id));
        this.observabilityService.stop(getClass().getSimpleName() + ":deleteArticle");
    }

    public void clearAllArticles(){
        this.observabilityService.start(getClass().getSimpleName() + ":clearAllArticles");
        repository.deleteAll();
        this.observabilityService.stop(getClass().getSimpleName() + ":clearAllArticles");
    }
}
