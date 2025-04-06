package ru.hpclab.hl.module1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Download;
import ru.hpclab.hl.module1.service.DownloadService;

import java.util.List;

@RestController
@RequestMapping("/downloads")
public class DownloadController {
    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    // Добавление нового скачивания
    @PostMapping
    public ResponseEntity<Download> addDownload(@RequestBody Download download) {
        Download savedDownload = downloadService.addDownload(download);
        return new ResponseEntity<>(savedDownload, HttpStatus.CREATED);
    }

    // Получение скачивания по ID
    @GetMapping("/{id}")
    public ResponseEntity<Download> getDownload(@PathVariable String id) {
        Download download = downloadService.getDownload(id);
        return download != null ? new ResponseEntity<>(download, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Получение всех скачиваний
    @GetMapping
    public ResponseEntity<List<Download>> getAllDownloads() {
        List<Download> downloads = downloadService.getAllDownloads();
        return new ResponseEntity<>(downloads, HttpStatus.OK);
    }

    // Удаление скачивания по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDownload(@PathVariable String id) {
        downloadService.deleteDownload(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
