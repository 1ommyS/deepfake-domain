package ru.mai.zaytsevvagen.deepfake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.mai.zaytsevvagen.deepfake.aspect.LogExecutionTime;
import ru.mai.zaytsevvagen.deepfake.dto.ImageInfo;

/**
 * @author 1ommy
 * @version 01.04.2023
 */
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, ImageInfo> kafkaTemplate;

    @LogExecutionTime
    public void sendMessage(String topic, ImageInfo msg) {
        kafkaTemplate.send(topic, msg);
    }
}
