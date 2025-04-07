package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.repository.DownloadRepository;
import ru.hpclab.hl.module1.model.Download;
import java.time.Month;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class DownloadActivityService {
    private final DownloadRepository downloadRepository;

    public DownloadActivityService(DownloadRepository downloadRepository) {
        this.downloadRepository = downloadRepository;
    }

    // Метод для получения статистики по скачиваниям по месяцам и форматам
    public Map<String, Map<String, Long>> getMonthlyDownloadStatistics() {
        List<Object[]> rawData = downloadRepository.getDownloadActivityPerMonth();
        Map<String, Map<String, Long>> result = new HashMap<>();

        for (Object[] record : rawData) {
            // Получаем месяц, формат и количество скачиваний
            int month = ((Number) record[0]).intValue();
//            Download.DownloadFormat format = Download.DownloadFormat.valueOf((String) record[1]); // Изменено v2
            String format = (String) record[1];                                                     // Изменено v1
            long count = ((Number) record[2]).longValue();

            // Формируем ключ месяца как "Месяц"
            String monthName = Month.of(month).name();

            // Если еще нет записи для месяца, создаем новый Map для форматов
            result.putIfAbsent(monthName, new HashMap<>());
//            result.get(monthName).put(String.valueOf(format), count);   // Изменено v2
            result.get(monthName).put(format, count);                     // Изменено v1
        }

        return result;
    }
}
