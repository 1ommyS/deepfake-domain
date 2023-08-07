package ru.mai.zaytsevvagen.deepfake.util;


import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.mai.zaytsevvagen.deepfake.aspect.LogExecutionTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 1ommy
 * @version 19.03.2023
 */
@Component
public class FileSavingUtil {
    private String path = "src/main/resources/static/files/";

    @LogExecutionTime
    public String saveFileToFileSystem(MultipartFile file) throws IOException, IllegalArgumentException {
        byte[] bytes = file.getBytes();
        if (bytes.length == 0) throw new IllegalArgumentException("File is empty");
        if (Objects.equals(file.getOriginalFilename(), Strings.EMPTY))
            throw new IllegalArgumentException("Filename is empty string");

        String path = this.path;

        UUID file_name = UUID.nameUUIDFromBytes(bytes);

        String originalFilename = file.getOriginalFilename();
        int dotIndex = originalFilename.lastIndexOf('.');
        var extension = (dotIndex == -1) ? "" : originalFilename.substring(dotIndex + 1);

        String pathname = path + file_name.toString() + "." + extension;
        File newFile = new File(pathname);
        newFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(bytes);
        fos.close();
        return pathname;
    }

    @LogExecutionTime
    public String saveFileToFileSystem(byte[] bytes) throws IOException {

        UUID file_name = UUID.nameUUIDFromBytes(bytes);


        String pathname = path + file_name.toString() + ".jpg";
        File newFile = new File(pathname);
        newFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(bytes);
        fos.close();
        return pathname;
    }
}