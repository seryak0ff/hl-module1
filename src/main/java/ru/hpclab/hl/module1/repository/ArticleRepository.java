package ru.hpclab.hl.module1.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import ru.hpclab.hl.module1.controller.exeption.ArticleException;
import ru.hpclab.hl.module1.controller.exeption.UserException;
import ru.hpclab.hl.module1.model.Article;
//import ru.hpclab.hl.module1.model.User;

import java.util.*;

import static java.lang.String.format;

@Repository
public class ArticleRepository {

    public static final String ARTICLE_NOT_FOUND_MSG = "Article with ID %s not found";
    public static final String ARTICLE_EXISTS_MSG = "Article with ID %s is already exists";

    private final Map<UUID, Article> articles = new HashMap<>();

    public List<Article> findAll() { return new ArrayList<>(articles.values());}

    public Article findById(UUID id) {
        final var article = articles.get(id);
        if (article == null) {
            throw new ArticleException(format(ARTICLE_NOT_FOUND_MSG, id));
        }
        return article;
    }

    public void delete(UUID id) {
        final var removed = articles.remove(id);
        if (removed == null) {
            throw new ArticleException(format(ARTICLE_NOT_FOUND_MSG, id));
        }
    }

    public Article save(Article article) {
        if (ObjectUtils.isEmpty(article.getIdentifier())) {
            article.setIdentifier(UUID.randomUUID());
        }

        final var articleData = articles.get(article.getIdentifier());
        if (articleData != null) {
            throw new ArticleException(format(ARTICLE_EXISTS_MSG, article.getIdentifier()));
        }

        articles.put(article.getIdentifier(), article);

        return article;
    }

    public Article put(Article article) {
        final var articleData = articles.get(article.getIdentifier());
        if (articleData == null) {
            throw new UserException(format(ARTICLE_NOT_FOUND_MSG, article.getIdentifier()));
        }

        final var removed = articles.remove(article.getIdentifier());
        if (removed != null) {
            articles.put(article.getIdentifier(), article);
        } else {
            throw new ArticleException(format(ARTICLE_NOT_FOUND_MSG, article.getIdentifier()));
        }

        return article;
    }

    public void clear(){articles.clear();}
}
