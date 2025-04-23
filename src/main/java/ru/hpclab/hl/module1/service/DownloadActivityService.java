package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.repository.DownloadRepository;
import java.time.Month;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

@Service
public class DownloadActivityService {
    private final DownloadRepository downloadRepository;

    public DownloadActivityService(DownloadRepository downloadRepository) {
        this.downloadRepository = downloadRepository;
    }

    public Map<String, Map<String, Map<String, Long>>> getUniversityDownloadStatistics() {
        List<Object[]> rawData = downloadRepository.getDownloadsStatisticsByUniversityAndFormat();
        Map<String, Map<String, Map<String, Long>>> result = new TreeMap<>();

        for (Object[] record : rawData) {
            int monthNumber = ((Number) record[0]).intValue();
            String monthName = Month.of(monthNumber).name();
            String university = (String) record[1];
            String format = ((Enum<?>) record[2]).name();
            long count = ((Number) record[3]).longValue();

            result.computeIfAbsent(monthName, k -> new HashMap<>())
                    .computeIfAbsent(university, k -> new HashMap<>())
                    .put(format, count);
        }

        return result;
    }
}
