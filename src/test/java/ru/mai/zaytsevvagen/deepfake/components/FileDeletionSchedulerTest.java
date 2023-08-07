package ru.mai.zaytsevvagen.deepfake.components;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mai.zaytsevvagen.deepfake.entity.Role;
import ru.mai.zaytsevvagen.deepfake.entity.User;
import ru.mai.zaytsevvagen.deepfake.repository.FileRepository;
import ru.mai.zaytsevvagen.deepfake.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 1ommy
 * @version 01.04.2023
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileDeletionSchedulerTest {

    @Autowired
    private FileRepository fileRepository;


    @Autowired
    private FileDeletionScheduler fileDeletionScheduler;

    @Test
    void testFileDeletionScheduler() throws IOException {
        // Создаем файл для удаления
        String path = "/Users/1ommy/deepfake_repos/deepfake-domain/src/main/resources/static/files/testFile.txt";
        File file = new File(path);
        boolean isCreated = file.createNewFile();
        assertTrue(isCreated);

        // Создаем объект File, который будет храниться в базе данных
        LocalDateTime now = LocalDateTime.now();
        ru.mai.zaytsevvagen.deepfake.entity.File fileToDelete = ru.mai.zaytsevvagen.deepfake.entity.File.builder()
                .originalPath(path)
                .user(User.builder()
                        .firstname("ivanert")
                        .lastname("berezutskiy")
                        .email("ivan32berezutskiy@gmail.com")
                        .password("12345678")
                        .role(Role.MEMBER)
                        .build())
                .deleteAt(now)
                .build();
        fileRepository.save(fileToDelete);

        // Удаляем файл через класс FileDeletionScheduler
        fileDeletionScheduler.deleteFiles();

        // Проверяем, что файл был удален
        assertFalse(file.exists());

        // Проверяем, что объект File был удален из базы данных
        assertFalse(fileRepository.findById(fileToDelete.getId()).isPresent());
    }
}
