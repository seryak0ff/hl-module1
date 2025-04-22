package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hpclab.hl.module1.model.Download;

import java.util.List;
import java.util.UUID;

public interface DownloadRepository extends JpaRepository<Download, UUID> {

    // Метод для получения статистики по месяцам и форматам
    @Query("SELECT EXTRACT(MONTH FROM d.downloadDate), u.university, d.format, COUNT(d) " +
            "FROM Download d JOIN User u ON d.userId = u.id " +
            "GROUP BY EXTRACT(MONTH FROM d.downloadDate), u.university, d.format " +
            "ORDER BY EXTRACT(MONTH FROM d.downloadDate), u.university")
    List<Object[]> getDownloadsStatisticsByUniversityAndFormat();


    // Метод для получения статистики по месяцам и форматам
//    @Query("SELECT EXTRACT(MONTH FROM d.downloadDate), CAST(d.format AS string), COUNT(d) " +
//            "FROM Download d " +
//            "GROUP BY EXTRACT(MONTH FROM d.downloadDate), d.format " +
//            "ORDER BY EXTRACT(MONTH FROM d.downloadDate) ASC")
//    List<Object[]> getDownloadActivityPerMonth();
}


