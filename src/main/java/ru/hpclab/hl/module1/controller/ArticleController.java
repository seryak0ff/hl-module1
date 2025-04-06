package ru.hpclab.hl.module1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Article;
import ru.hpclab.hl.module1.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // Добавление новой статьи
    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article savedArticle = articleService.addArticle(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    // Получение статьи по ID
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable String id) {
        Article article = articleService.getArticle(id);
        return article != null ? new ResponseEntity<>(article, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Получение всех статей
    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Удаление статьи по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
