package ru.mai.zaytsevvagen.deepfake.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mai.zaytsevvagen.deepfake.dto.AuthenticationRequest;
import ru.mai.zaytsevvagen.deepfake.dto.AuthenticationResponse;
import ru.mai.zaytsevvagen.deepfake.dto.RegisterRequest;
import ru.mai.zaytsevvagen.deepfake.entity.Role;
import ru.mai.zaytsevvagen.deepfake.entity.User;
import ru.mai.zaytsevvagen.deepfake.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void register_returns_authentication_response_with_id() {
        // Arrange
        var authenticationService = new AuthenticationService(repository, passwordEncoder, authenticationManager);
        var registerRequest = new RegisterRequest("John", "Doe", "Doe", "password");
        User savedUser = new User(1, "John", "Doe", "john.doe@example.com", "encoded_password", Role.MEMBER);
        when(repository.save(any(User.class))).thenReturn(savedUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");

        // Act
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

        // Assert
        assertThat(authenticationResponse.getUsername()).isEqualTo("John Doe");
        assertThat(authenticationResponse.getId()).isEqualTo(1L);
        verify(repository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void authenticate_returns_authentication_response_with_id() {
        // Arrange
        AuthenticationService authenticationService = new AuthenticationService(repository, passwordEncoder, authenticationManager);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john.doe@example.com", "password");
        User user = new User(1, "John", "Doe", "john.doe@example.com", "encoded_password", Role.MEMBER);
        when(repository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));

        // Act
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        // Assert
        assertThat(authenticationResponse.getUsername()).isEqualTo("John Doe");
        assertThat(authenticationResponse.getId()).isEqualTo(1L);
        verify(repository, times(1)).findByEmail(anyString());
    }

    @Test
    void authenticate_throws_exception_when_user_not_found() {
        // Arrange
        AuthenticationService authenticationService = new AuthenticationService(repository, passwordEncoder, authenticationManager);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john.doe@example.com", "password");
        when(repository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(authenticationRequest));
        verify(repository, times(1)).findByEmail(anyString());
    }
}
