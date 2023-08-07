package ru.mai.zaytsevvagen.deepfake.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 1ommy
 * @version 02.04.2023
 */
@AllArgsConstructor
@Getter
@ToString
public enum MediaTypes {
    PHOTO("photo"),
    VIDEO("video");

    private final String title;

}
