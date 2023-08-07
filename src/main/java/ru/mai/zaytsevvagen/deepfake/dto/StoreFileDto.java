package ru.mai.zaytsevvagen.deepfake.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 1ommy
 * @version 20.03.2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreFileDto {

    @NotNull
    private MultipartFile file;

    @NotBlank
    private String username;

    @NotBlank
    @Min(1)
    @Max(30)
    private int days;
}
