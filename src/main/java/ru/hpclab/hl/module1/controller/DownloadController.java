package ru.hpclab.hl.module1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Download;
import ru.hpclab.hl.module1.service.DownloadService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/downloads")
public class DownloadController {
    private final DownloadService downloadService;

    @Autowired
    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    // Получить все записи о скачиваниях
    @GetMapping
    public List<Download> getDownloads() {
        return downloadService.getAllDownloads();
    }

    // Получить запись о скачивании по ID
    @GetMapping("/{id}")
    public Download getDownloadById(@PathVariable String id) {
        return downloadService.getDownloadById(id);
    }

    // Удалить запись о скачивании
    @DeleteMapping("/{id}")
    public void deleteDownload(@PathVariable String id) {
        downloadService.deleteDownload(id);
    }

    // Создать новую запись о скачивании
    @PostMapping
    public Download saveDownload(
            @RequestParam String userId,
            @RequestParam String articleId,
            @RequestParam String format) {
        return downloadService.saveDownload(userId, articleId, format);
    }

    // Обновить запись о скачивании
    @PutMapping("/{id}")
    public Download updateDownload(@PathVariable String id, @RequestBody Download download) {
        return downloadService.updateDownload(id, download);
    }

    // Получить все скачивания пользователя
    @GetMapping("/user/{userId}")
    public List<Download> getUserDownloads(@PathVariable String userId) {
        return downloadService.getUserDownloads(userId);
    }

    // Получить статистику скачиваний по месяцам и форматам
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Map<String, Long>>> getDownloadStatistics() {
        Map<String, Map<String, Long>> statistics = downloadService.getDownloadStatistics();
        return ResponseEntity.ok(statistics);
    }
}
