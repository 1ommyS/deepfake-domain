package ru.mai.zaytsevvagen.deepfake.dto;

/**
 * @author 1ommy
 * @version 19.03.2023
 */

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDto {
    private String message;
    private Integer status;
}
