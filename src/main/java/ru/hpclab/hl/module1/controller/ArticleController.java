package ru.hpclab.hl.module1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Article;
import ru.hpclab.hl.module1.service.ArticleService;

import java.util.List;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<Article> getArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/articles/{id}")
    public Article getArticleById(@PathVariable String id) {
        return articleService.getArticleById(id);
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable String id) {
        articleService.deleteArticle(id);
    }

    @PostMapping(value = "/articles/")
    public Article saveArticle(@RequestBody Article client) {
        return articleService.saveArticle(client);
    }

    @PutMapping(value = "/articles/{id}")
    public Article updateArticle(@PathVariable(required = false) String id, @RequestBody Article article) {
        return articleService.updateArticle(id, article);
    }
}
