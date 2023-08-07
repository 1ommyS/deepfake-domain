package ru.mai.zaytsevvagen.deepfake.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mai.zaytsevvagen.deepfake.entity.User;

/**
 * @author 1ommy
 * @version 19.03.2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private byte[] deepfake;
    private byte[] original;

    private User user;
}
