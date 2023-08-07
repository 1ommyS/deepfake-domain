package ru.mai.zaytsevvagen.deepfake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mai.zaytsevvagen.deepfake.aspect.LogExecutionTime;
import ru.mai.zaytsevvagen.deepfake.dto.ImageInfo;
import ru.mai.zaytsevvagen.deepfake.repository.FileRepository;
import ru.mai.zaytsevvagen.deepfake.util.FileSavingUtil;

import java.io.IOException;

/**
 * @author 1ommy
 * @version 01.04.2023
 */
@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final FileRepository repository;
    private final FileSavingUtil fileSavingUtil;

    @KafkaListener(topics = "ml_topic_get_deepfake", groupId = "myGroup")
    @LogExecutionTime
    void listener(ImageInfo data) throws IOException {
        try {
            String path = fileSavingUtil.saveFileToFileSystem(data.getImage());
            repository.updateDeepfakePathById(data.getId(), path);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
