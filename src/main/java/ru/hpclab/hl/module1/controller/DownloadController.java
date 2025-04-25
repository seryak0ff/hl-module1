package ru.hpclab.hl.module1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Download;
import ru.hpclab.hl.module1.service.DownloadService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Новый эндпоинт для очистки всех данных
    @Operation(summary = "Clear all downloads")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All downloads deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearAllDownloads() {
        downloadService.clearAllDownloads();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download-activity")
    @Operation(summary = "Get download activity statistics", description = "Returns download statistics grouped by month, university and format")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved download statistics"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, Object>> getDownloadActivity() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", downloadService.getDownloadActivity());
        return ResponseEntity.ok(response);
    }
}
