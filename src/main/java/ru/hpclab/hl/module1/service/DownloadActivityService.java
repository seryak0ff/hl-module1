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


//    public Map<String, Map<String, Map<String, Long>>> getUniversityDownloadStatistics() {
//        List<Object[]> rawData = downloadRepository.getDownloadsStatisticsByUniversityAndFormat();
//        Map<String, Map<String, Map<String, Long>>> result = new TreeMap<>();
//
//        for (Object[] record : rawData) {
//            int monthNumber = ((Number) record[1]).intValue();
//            String monthName = Month.of(monthNumber).name();
//            String university = (String) record[2];
//            String format = ((Enum<?>) record[3]).name();
//            long count = ((Number) record[4]).longValue();
//
//            // Добавляем университет если его нет
//            result.putIfAbsent(monthName, new HashMap<>());
//
//            // Добавляем формат если его нет для этого университета
//            result.get(monthName).putIfAbsent(university, new HashMap<>());
//
//            // Добавляем количество скачиваний для формата
//            result.get(monthName).get(university).put(format, count);
//        }
//
//        return result;
//    }


    // Метод для получения статистики по скачиваниям по месяцам университетам и форматам
//    public Map<String, Map<String, Long>> getMonthlyDownloadStatistics() {
//        List<Object[]> rawData = downloadRepository.getDownloadActivityPerMonth(); //TODO
//        Map<String, Map<String, Long>> result = new HashMap<>();
//
//        for (Object[] record : rawData) {
//            // Получаем месяц, формат и количество скачиваний
//            int month = ((Number) record[0]).intValue();
////            Download.DownloadFormat format = Download.DownloadFormat.valueOf((String) record[1]); // Изменено v2
//            String format = (String) record[1];                                                     // Изменено v1
//            long count = ((Number) record[2]).longValue();
//
//            // Формируем ключ месяца как "Месяц"
//            String monthName = Month.of(month).name();
//
//            // Если еще нет записи для месяца, создаем новый Map для форматов
//            result.putIfAbsent(monthName, new HashMap<>());
////            result.get(monthName).put(String.valueOf(format), count);   // Изменено v2
//            result.get(monthName).put(format, count);                     // Изменено v1
//        }
//
//        return result;
//    }
}
