package ru.hpclab.hl.module1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Article;
import ru.hpclab.hl.module1.service.ArticleService;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final ObservabilityService observabilityService;

    public ArticleController(ObservabilityService observabilityService, ArticleService articleService) {
        this.observabilityService = observabilityService;
        this.articleService = articleService;
    }

    // Добавление новой статьи
    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        this.observabilityService.start(getClass().getSimpleName() + ":addArticle - Controller");
        Article savedArticle = articleService.addArticle(article);
        ResponseEntity<Article> temp = new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
        this.observabilityService.stop(getClass().getSimpleName() + ":addArticle - Controller");
        return temp;
    }

    // Получение статьи по ID
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":getArticle - Controller");
        Article article = articleService.getArticle(id);
        ResponseEntity<Article> temp = article != null ? new ResponseEntity<>(article, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.observabilityService.stop(getClass().getSimpleName() + ":getArticle - Controller");
        return temp;
    }

    // Получение всех статей
    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        this.observabilityService.start(getClass().getSimpleName() + ":getAllArticles - Controller");
        List<Article> articles = articleService.getAllArticles();
        ResponseEntity<List<Article>> temp = new ResponseEntity<>(articles, HttpStatus.OK);
        this.observabilityService.stop(getClass().getSimpleName() + ":getAllArticles - Controller");
        return temp;
    }

    // Удаление статьи по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":deleteArticle - Controller");
        articleService.deleteArticle(id);
        ResponseEntity<Void> temp = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        this.observabilityService.stop(getClass().getSimpleName() + ":deleteArticle - Controller");
        return temp;
    }

    // Новый эндпоинт для очистки всех данных
    @Operation(summary = "Clear all articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All articles deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearAllArticles() {
        this.observabilityService.start(getClass().getSimpleName() + ":deleteDownload - Controller");
        articleService.clearAllArticles();
        ResponseEntity<Void> temp = ResponseEntity.noContent().build();
        this.observabilityService.stop(getClass().getSimpleName() + ":deleteDownload - Controller");
        return temp;
    }
}
