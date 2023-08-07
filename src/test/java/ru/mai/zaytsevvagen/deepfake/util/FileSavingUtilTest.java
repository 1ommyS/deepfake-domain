package ru.mai.zaytsevvagen.deepfake.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileSavingUtilTest {

    private final FileSavingUtil fileSavingUtil = new FileSavingUtil();

    @Test
    void saveFileToFileSystem_shouldReturnPathname_whenFileIsValid() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-file.txt",
                "text/plain",
                "Test file content".getBytes()
        );

        // Act
        String pathname = fileSavingUtil.saveFileToFileSystem(file);

        // Assert
        File savedFile = new File(pathname);
        assertTrue(savedFile.exists());
    }

    @Test
    void saveFileToFileSystem_shouldThrowIllegalArgumentException_whenFileIsEmpty() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-file.txt",
                "text/plain",
                new byte[0]
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fileSavingUtil.saveFileToFileSystem(file));
    }

    @Test
    void saveFileToFileSystem_shouldThrowIllegalArgumentException_whenFilenameIsEmpty() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "",
                "text/plain",
                "Test file content".getBytes()
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fileSavingUtil.saveFileToFileSystem(file));
    }

    @Test
    void saveFileToFileSystem_shouldReturnPathname_whenByteArrayIsValid() throws IOException {
        // Arrange
        byte[] bytes = "Test file content".getBytes();

        // Act
        String pathname = fileSavingUtil.saveFileToFileSystem(bytes);

        // Assert
        File savedFile = new File(pathname);
        assertTrue(savedFile.exists());
    }
}