package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Download;
import ru.hpclab.hl.module1.model.User;
import ru.hpclab.hl.module1.model.Article;
import ru.hpclab.hl.module1.repository.DownloadRepository;
import ru.hpclab.hl.module1.repository.UserRepository;
import ru.hpclab.hl.module1.repository.ArticleRepository; // Предполагается, что такой класс существует

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DownloadService {
    private final DownloadRepository downloadRepository;
    private final UserRepository userRepository;      // Для работы с пользователями
    private final ArticleRepository articleRepository; // Для работы со статьями

    public DownloadService(DownloadRepository downloadRepository,
                           UserRepository userRepository,
                           ArticleRepository articleRepository) {
        this.downloadRepository = downloadRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    // Получить все записи о скачиваниях
    public List<Download> getAllDownloads() {
        return downloadRepository.findAll();
    }

    // Получить запись о скачивании по ID
    public Download getDownloadById(String id) {
        return downloadRepository.findById(UUID.fromString(id));
    }

    // Создать новую запись о скачивании
    public Download saveDownload(String userId, String articleId, String format) {
        User user = userRepository.findById(UUID.fromString(userId));
        Article article = articleRepository.findById(UUID.fromString(articleId));

        // Проверка подписки пользователя
        if (user.getSubscriptionEndDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("User subscription has expired");
        }

        // Валидация формата
        if (!"PDF".equals(format) && !"HTML".equals(format)) {
            throw new IllegalArgumentException("Invalid format. Use PDF or HTML");
        }

        Download download = new Download(user, article, LocalDate.now(), format);
        return downloadRepository.save(download);
    }

    // Удалить запись о скачивании
    public void deleteDownload(String id) {
        downloadRepository.delete(UUID.fromString(id));
    }

    // Обновить запись о скачивании
    public Download updateDownload(String id, Download download) {
        download.setId(UUID.fromString(id));
        return downloadRepository.put(download);
    }

    // Получить все скачивания пользователя
    public List<Download> getUserDownloads(String userId) {
        return downloadRepository.findByUserId(UUID.fromString(userId));
    }

    // Получить статистику скачиваний по месяцам и форматам
    public Map<String, Map<String, Long>> getDownloadStatistics() {
        return downloadRepository.getDownloadStatistics();
    }
}
