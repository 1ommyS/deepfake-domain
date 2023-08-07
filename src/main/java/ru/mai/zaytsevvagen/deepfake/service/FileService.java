package ru.mai.zaytsevvagen.deepfake.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mai.zaytsevvagen.deepfake.aspect.LogExecutionTime;
import ru.mai.zaytsevvagen.deepfake.dto.FileDTO;
import ru.mai.zaytsevvagen.deepfake.dto.FileUploadDto;
import ru.mai.zaytsevvagen.deepfake.dto.ImageInfo;
import ru.mai.zaytsevvagen.deepfake.dto.MediaTypes;
import ru.mai.zaytsevvagen.deepfake.entity.File;
import ru.mai.zaytsevvagen.deepfake.entity.User;
import ru.mai.zaytsevvagen.deepfake.exception.UserNotFoundException;
import ru.mai.zaytsevvagen.deepfake.repository.FileRepository;
import ru.mai.zaytsevvagen.deepfake.repository.UserRepository;
import ru.mai.zaytsevvagen.deepfake.util.FileSavingUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 1ommy
 * @version 28.02.2023
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final FileRepository repository;
    private final UserRepository userRepository;
    private final FileSavingUtil fileSavingUtil;
    private final KafkaProducerService kafkaProducerService;
    private String path = "src/main/resources/static/files/";

    @LogExecutionTime
    public List<FileDTO> getAllUserFiles(int userId) throws UserNotFoundException {

        log.info("FileService.getAllUserFiles -- input. User id: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        log.info("FileService.getAllUserFiles. User was found. Username is: {}.", user.getUsername());

        return repository.findAllByUser(user).stream().map(e -> {
            try {
                log.info("FileService.getAllUserFiles Reading files");

                FileInputStream inputStreamOriginal = new FileInputStream(e.getOriginalPath());
                FileInputStream inputStreamDeepfake = e.getDeepfakePath() != null ? new FileInputStream(e.getDeepfakePath()) : null;

                log.info("FileService.getAllUserFiles Files were successfully read");

                return FileDTO.builder()
                        .user(e.getUser())
                        .deepfake(inputStreamDeepfake != null ? IOUtils.toByteArray(inputStreamDeepfake) : null)
                        .original(IOUtils.toByteArray(inputStreamOriginal))
                        .build();
            } catch (IOException ex) {
                log.error("FileService.getAllUserFiles Error. {}", ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }
        }).collect(Collectors.toList());
    }


    @LogExecutionTime
    public FileUploadDto storeFile(MultipartFile file, int id, int days, int number) throws Exception {
        log.info("FileService.storeFile -- input. ID: {}, days: {}", id, days);

        String path = fileSavingUtil.saveFileToFileSystem(file);
        log.info("FileService.storeFile. File was successfully saved to the file system.");

        Optional<User> user = userRepository.findById(id);
        log.info("FileService.storeFile user with id {} was successfully found. Username is {}", id, user.get().getUsername());

        var fileToSave = ru.mai.zaytsevvagen.deepfake.entity.File
                .builder()
                .originalPath(path)
                .user(user.orElseThrow())
                .deleteAt(LocalDateTime.now().plusDays(days))
                .build();

        log.info("File object was successfully created");

        try {
            File saved = repository.save(fileToSave);

            String originalFilename = file.getOriginalFilename();
            var extension = (originalFilename.lastIndexOf(".") == -1) ? "" : originalFilename.substring(originalFilename.lastIndexOf(".") + 1);


            var photoExtensions = ".jpg, .png, .svg";
            var type = photoExtensions.lastIndexOf(extension) == -1 ? MediaTypes.VIDEO.getTitle() : MediaTypes.PHOTO.getTitle();

            kafkaProducerService.sendMessage("ml_topic", ImageInfo.builder()
                    .type(type)
                    .extension(extension)
                    .image(file.getBytes())
                    .number(number)
                    .id(saved.getId())
                    .build());

            return FileUploadDto.builder()
                    .message("This file was successfully uploaded")
                    .status(201)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return FileUploadDto.builder()
                    .message("Something went wrong")
                    .status(400)
                    .build();
        }
    }

}
