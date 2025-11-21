package com.works.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class LogService {

    private static final String LOG_DIR = "logs";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Async
    public void writeLog(String sessionId, String username, long time, String url, String agent) {
        try {
            // Logs klasörünü oluştur (yoksa)
            Path logDirectory = Paths.get(LOG_DIR);
            if (!Files.exists(logDirectory)) {
                Files.createDirectories(logDirectory);
            }

            // Günlük log dosyası adını oluştur (örn: application-2025-11-21.log)
            String fileName = String.format("application-%s.log", LocalDateTime.now().format(dateFormatter));
            String logFilePath = LOG_DIR + "/" + fileName;

            // Log mesajını yaz
            try (FileWriter fw = new FileWriter(logFilePath, true);
                 PrintWriter pw = new PrintWriter(fw)) {
                
                String logMessage = String.format("[%s] Session Id: %s | User: %s | Time: %d | URL: %s | Agent: %s",
                        LocalDateTime.now().format(timeFormatter),
                        sessionId,
                        username,
                        time,
                        url,
                        agent);
                
                pw.println(logMessage);
            }
            
        } catch (IOException e) {
            log.error("Log yazma hatası: ", e);
        }
    }
}
