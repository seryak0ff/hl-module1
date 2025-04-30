package ru.hpclab.hl.module1.kafkamodule.dispatch;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hpclab.hl.module1.apimodule.model.Article;
import ru.hpclab.hl.module1.apimodule.model.Download;
import ru.hpclab.hl.module1.apimodule.model.User;
import ru.hpclab.hl.module1.kafkamodule.queue.EntityType;
import ru.hpclab.hl.module1.kafkamodule.queue.KafkaOperationMessage;
import ru.hpclab.hl.module1.kafkamodule.queue.OperationType;
import ru.hpclab.hl.module1.apimodule.service.ArticleService;
import ru.hpclab.hl.module1.apimodule.service.DownloadService;
import ru.hpclab.hl.module1.apimodule.service.UserService;

@Component
@RequiredArgsConstructor
public class KafkaMessageDispatcher {
    private final UserService userService;
    private final ArticleService articleService;
    private final DownloadService downloadService;
    private final ObjectMapper objectMapper;

    public void dispatch(KafkaOperationMessage msg) {
        EntityType entity = msg.getEntity();
        OperationType operation = msg.getOperation();

        switch (entity) {
            case USER -> handleUser(operation, msg.getPayload());
            case ARTICLE -> handleArticle(operation, msg.getPayload());
            case DOWNLOAD -> handleDownload(operation, msg.getPayload());
        }
    }

    private void handleUser(OperationType op, JsonNode payload) {
        switch (op) {
            case POST -> userService.addUser(deserialize(payload, User.class));
            case PUT -> userService.addUser(deserialize(payload, User.class)); // или реализовать update если нужно
            case DELETE -> userService.deleteUser(payload.get("id").asText());
            case CLEAR -> userService.clearAllUsers();
        }
    }

    private void handleArticle(OperationType op, JsonNode payload) {
        switch (op) {
            case POST -> articleService.addArticle(deserialize(payload, Article.class));
            case PUT -> articleService.addArticle(deserialize(payload, Article.class)); // или update
            case DELETE -> articleService.deleteArticle(payload.get("id").asText());
            case CLEAR -> articleService.clearAllArticles();
        }
    }

    private void handleDownload(OperationType op, JsonNode payload) {
        switch (op) {
            case POST -> downloadService.addDownload(deserialize(payload, Download.class));
            case PUT -> downloadService.addDownload(deserialize(payload, Download.class)); // или update
            case DELETE -> downloadService.deleteDownload(payload.get("id").asText());
            case CLEAR -> downloadService.clearAllDownloads();
        }
    }

    private <T> T deserialize(JsonNode node, Class<T> clazz) {
        try {
            return objectMapper.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize payload to " + clazz.getSimpleName(), e);
        }
    }
}
