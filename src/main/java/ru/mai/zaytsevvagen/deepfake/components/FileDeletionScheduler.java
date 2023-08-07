package ru.mai.zaytsevvagen.deepfake.components;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mai.zaytsevvagen.deepfake.entity.File;
import ru.mai.zaytsevvagen.deepfake.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.*;

/**
 * @author 1ommy
 * @version 19.03.2023
 */
@Component
@RequiredArgsConstructor
public class FileDeletionScheduler {

    private final FileRepository fileRepository;

    @Scheduled(cron = "0 0 0 * * *") // Запускать каждый день в полночь
    public void deleteFiles() {
        LocalDateTime now = now();
        List<File> filesToDelete = fileRepository.findByDeleteAtLessThanEqual(now);
        for (File file : filesToDelete) {
            try {
                Files.delete(Paths.get(file.getOriginalPath()));
                fileRepository.delete(file);
            } catch (IOException e) {
                // Обработка ошибок при удалении файла
            }
        }
    }
}
