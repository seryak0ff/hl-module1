package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hpclab.hl.module1.model.Download;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DownloadRepository extends JpaRepository<Download, UUID> {

    // Метод для получения статистики по месяцам и форматам
    @Query("SELECT EXTRACT(MONTH FROM d.downloadDate) AS month, d.format, COUNT(d) AS count " +
            "FROM Download d " +
            "GROUP BY EXTRACT(MONTH FROM d.downloadDate), d.format " +
            "ORDER BY month ASC")
    List<Object[]> getDownloadActivityPerMonth();
}

//  Изменить таблицу!!!


//public interface DownloadRepository extends JpaRepository<Download, UUID> {
//    @Query("""
//        SELECT
//            EXTRACT(YEAR FROM d.downloadDate) AS year,
//            EXTRACT(MONTH FROM d.downloadDate) AS month,
//            d.format AS format,
//            COUNT(d.id) AS downloadCount
//        FROM Download d
//        GROUP BY year, month, d.format
//        ORDER BY year DESC, month DESC, d.format
//    """)
//    List<Object[]> countDownloadsByMonthAndFormat();


