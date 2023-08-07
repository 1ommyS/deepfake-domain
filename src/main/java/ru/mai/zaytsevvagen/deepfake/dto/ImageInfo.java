package ru.mai.zaytsevvagen.deepfake.dto;

import lombok.*;

/**
 * @author 1ommy
 * @version 01.04.2023
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {
    private byte[] image;
    private Integer id;
    private String extension;
    private int number;
    private String type;
}
