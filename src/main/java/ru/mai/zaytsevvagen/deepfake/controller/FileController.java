package ru.mai.zaytsevvagen.deepfake.controller;

/**
 * @author 1ommy
 * @version 19.03.2023
 */

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mai.zaytsevvagen.deepfake.dto.FileDTO;
import ru.mai.zaytsevvagen.deepfake.dto.FileUploadDto;
import ru.mai.zaytsevvagen.deepfake.exception.UserNotFoundException;
import ru.mai.zaytsevvagen.deepfake.service.FileService;

import java.util.List;

/**
 * @author 1ommy
 * @version 28.02.2023
 */

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 36000)
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/{userId}/{days}/{number}")
    public FileUploadDto storeFile(@RequestBody MultipartFile file, @PathVariable("userId") int id, @PathVariable("days") int days, @PathVariable("number") int number) throws Exception {
        return fileService.storeFile(file, id, days, number);
    }

    @GetMapping
    public List<FileDTO> getAllUserFiles(@RequestParam("userId") int id) throws UserNotFoundException {
        return fileService.getAllUserFiles(id);
    }
}