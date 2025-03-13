package ru.hpclab.hl.module1.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import ru.hpclab.hl.module1.controller.exeption.DownloadException;
import ru.hpclab.hl.module1.model.Download;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Repository
public class DownloadRepository {

    public static final String DOWNLOAD_NOT_FOUND_MSG = "Download with ID %s not found";
    public static final String DOWNLOAD_EXISTS_MSG = "Download with ID %s already exists";

    private final Map<UUID, Download> downloads = new HashMap<>();

    // Получить все записи о скачиваниях
    public List<Download> findAll() {
        return new ArrayList<>(downloads.values());
    }

    // Найти запись по ID
    public Download findById(UUID id) {
        final var download = downloads.get(id);
        if (download == null) {
            throw new DownloadException(format(DOWNLOAD_NOT_FOUND_MSG, id));
        }
        return download;
    }

    // Удалить запись по ID
    public void delete(UUID id) {
        final var removed = downloads.remove(id);
        if (removed == null) {
            throw new DownloadException(format(DOWNLOAD_NOT_FOUND_MSG, id));
        }
    }

    // Сохранить новую запись
    public Download save(Download download) {
        if (ObjectUtils.isEmpty(download.getId())) {
            download.setId(UUID.randomUUID());
        }

        final var downloadData = downloads.get(download.getId());
        if (downloadData != null) {
            throw new DownloadException(format(DOWNLOAD_EXISTS_MSG, download.getId()));
        }

        downloads.put(download.getId(), download);
        return download;
    }

    // Обновить существующую запись
    public Download put(Download download) {
        final var downloadData = downloads.get(download.getId());
        if (downloadData == null) {
            throw new DownloadException(format(DOWNLOAD_NOT_FOUND_MSG, download.getId()));
        }

        final var removed = downloads.remove(download.getId());
        if (removed != null) {
            downloads.put(download.getId(), download);
        } else {
            throw new DownloadException(format(DOWNLOAD_NOT_FOUND_MSG, download.getId()));
        }

        return download;
    }

    // Очистить хранилище
    public void clear() {
        downloads.clear();
    }

    // Найти все загрузки пользователя по его ID
    public List<Download> findByUserId(UUID userId) {
        return downloads.values().stream()
                .filter(download -> download.getUser().getIdentifier().equals(userId))
                .collect(Collectors.toList());
    }

    // Найти загрузки за период
    public List<Download> findByDownloadDateBetween(LocalDate startDate, LocalDate endDate) {
        return downloads.values().stream()
                .filter(download -> !download.getDownloadDate().isBefore(startDate) &&
                        !download.getDownloadDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    // Получить статистику скачиваний по месяцам и форматам
    public Map<String, Map<String, Long>> getDownloadStatistics() {
        Map<String, Map<String, Long>> stats = new HashMap<>();

        downloads.values().forEach(download -> {
            String yearMonth = download.getDownloadDate().getYear() + "-" +
                    String.format("%02d", download.getDownloadDate().getMonthValue());
            String format = download.getFormat();

            stats.computeIfAbsent(yearMonth, k -> new HashMap<>())
                    .merge(format, 1L, Long::sum);
        });

        return stats;
    }
}