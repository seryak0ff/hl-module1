package ru.hpclab.hl.module1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.service.DownloadActivityService;

import java.util.Map;

@RestController
@RequestMapping("/download-activity")
public class DownloadActivityController {
    private final DownloadActivityService downloadActivityService;

    public DownloadActivityController(DownloadActivityService downloadActivityService) {
        this.downloadActivityService = downloadActivityService;
    }

    // Получение статистики скачиваний по месяцам университетам и форматам
    @GetMapping
    public ResponseEntity<Map<String, Map<String, Map<String, Long>>>> getUniversityDownloadStatistics() {
        Map<String, Map<String, Map<String, Long>>> statistics =
                downloadActivityService.getUniversityDownloadStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    // Получение статистики скачиваний по месяцам и форматам
//    @GetMapping
//    public ResponseEntity<Map<String, Map<String, Long>>> getMonthlyDownloadStatistics() {
//        Map<String, Map<String, Long>> statistics = downloadActivityService.getMonthlyDownloadStatistics();
//        return new ResponseEntity<>(statistics, HttpStatus.OK);
//    }
}
