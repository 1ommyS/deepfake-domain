package ru.mai.zaytsevvagen.deepfake.dto;

/**
 * @author 1ommy
 * @version 19.03.2023
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String username;
    private Integer id;
}