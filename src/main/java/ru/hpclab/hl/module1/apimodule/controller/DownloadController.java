package ru.hpclab.hl.module1.apimodule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.apimodule.model.Download;
import ru.hpclab.hl.module1.apimodule.service.DownloadService;
import ru.hpclab.hl.module1.apimodule.service.statistics.ObservabilityService;

import java.util.List;

@RestController
@RequestMapping("/downloads")
public class DownloadController {
    private final DownloadService downloadService;
    private final ObservabilityService observabilityService;

    public DownloadController(ObservabilityService observabilityService, DownloadService downloadService) {
        this.observabilityService = observabilityService;
        this.downloadService = downloadService;
    }

    // Добавление нового скачивания
    @PostMapping
    public ResponseEntity<Download> addDownload(@RequestBody Download download) {
        this.observabilityService.start(getClass().getSimpleName() + ":addDownload - Controller");
        Download savedDownload = downloadService.addDownload(download);
        ResponseEntity<Download> temp = new ResponseEntity<>(savedDownload, HttpStatus.CREATED);
        this.observabilityService.stop(getClass().getSimpleName() + ":addDownload - Controller");
        return temp;
    }

    // Получение скачивания по ID
    @GetMapping("/{id}")
    public ResponseEntity<Download> getDownload(@PathVariable String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":getDownload - Controller");
        Download download = downloadService.getDownload(id);
        ResponseEntity<Download> temp = download != null ? new ResponseEntity<>(download, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.observabilityService.stop(getClass().getSimpleName() + ":getDownload - Controller");
        return temp;
    }

    // Получение всех скачиваний
    @GetMapping
    public ResponseEntity<List<Download>> getAllDownloads() {
        this.observabilityService.start(getClass().getSimpleName() + ":getAllDownloads - Controller");
        List<Download> downloads = downloadService.getAllDownloads();
        ResponseEntity<List<Download>> temp = new ResponseEntity<>(downloads, HttpStatus.OK);
        this.observabilityService.stop(getClass().getSimpleName() + ":getAllDownloads - Controller");
        return temp;
    }

    // Удаление скачивания по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDownload(@PathVariable String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":deleteDownload - Controller");
        downloadService.deleteDownload(id);
        ResponseEntity<Void> temp = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        this.observabilityService.stop(getClass().getSimpleName() + ":deleteDownload - Controller");
        return temp;
    }

    // Новый эндпоинт для очистки всех данных
    @Operation(summary = "Clear all downloads")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All downloads deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearAllDownloads() {
        this.observabilityService.start(getClass().getSimpleName() + ":deleteDownload - Controller");
        downloadService.clearAllDownloads();
        ResponseEntity<Void> temp = ResponseEntity.noContent().build();
        this.observabilityService.stop(getClass().getSimpleName() + ":deleteDownload - Controller");
        return temp;
    }
}
